package com.example.common.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.utils.sendRetrySnackbar
import com.example.data.domain.AuthRepo
import com.example.data.domain.LikesRepo
import com.example.local.datastore.auth.LoggingState
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val authRepository: AuthRepo,
    private val likesRepository: LikesRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AuthState()
    )

    // === Private helpers ===
    private fun updateState(transform: (AuthState) -> AuthState) {
        _authState.update(transform)
    }

    // === Auth & data ===
    private fun observeSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                authRepository.loggingState,
                authRepository.userSessionToken
            ) { loggingState, token ->
                loggingState to token
            }.collect { (loggingState, token) ->
                updateState {
                    it.copy(
                        isLogged = loggingState,
                        sessionToken = token
                    )
                }
                if ((loggingState is LoggingState.LoggedIn) and (_authState.value.sessionToken != null)) {
                    fetchLikesAmount()
                }
            }
        }
    }

    private fun getSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            updateState {
                it.copy(
                    isLoading = true,
                    incorrectEmail = false,
                    incorrectPassword = false,
                    isAuthBSOpened = false
                )
            }

            val response = authRepository.getSessionToken(_authState.value.email, _authState.value.password)
            when (response.error) {
                NetworkErrors.SUCCESS -> {
                    authRepository.saveUserSessionToken(response.response!!.token!!)
                }
                NetworkErrors.INCORRECT_EMAIL_OR_PASSWORD -> {
                    updateState {
                        it.copy(
                            incorrectEmail = true,
                            incorrectPassword = true,
                            isAuthBSOpened = true
                        )
                    }
                }
                else -> {
                    sendRetrySnackbar(
                        label = response.label!!,
                        action = { getSessionToken() }
                    )
                }
            }

            updateState { it.copy(isLoading = false) }
        }
    }

    private fun clearSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            authRepository.clearUserSessionToken()
        }
    }

    // === Likes ===
    private fun fetchLikesAmount() {
        viewModelScope.launch(dispatcherIo) {
            val response = likesRepository.getLikesAmount(_authState.value.sessionToken!!)
            updateState {
                it.copy(
                    likesError = false,
                    isLoading = true
                )
            }

            if (response.error == NetworkErrors.SUCCESS) {
                updateState {
                    it.copy(
                        likesAmount = response.response!!.size,
                        isLoading = false
                    )
                }
                if ((_authState.value.sessionToken != null) and (_authState.value.isLogged is LoggingState.LoggedIn)) {
                    fetchLikes()
                }
            } else {
                updateState {
                    it.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                sendRetrySnackbar(
                    label = "Проблема в получении избранных: ${response.label!!}",
                    action = { fetchLikesAmount() }
                )
            }
        }
    }

    private fun fetchLikes() {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isLoading = true) }

            val response = likesRepository.getLikes(
                _authState.value.sessionToken!!,
                _authState.value.likesAmount
            )

            if (response.error == NetworkErrors.SUCCESS) {
                updateState {
                    it.copy(
                        likes = response.response!!.data,
                        isLoading = false
                    )
                }
            } else {
                updateState {
                    it.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                sendRetrySnackbar(
                    label = "Проблема в получении избранных: ${response.label!!}",
                    action = { fetchLikesAmount() }
                )
            }
        }
    }

    private fun addLike(title: Data) {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isLoading = true) }

            val response = likesRepository.addLike(
                _authState.value.sessionToken!!,
                title.id
            )

            if (response.error == NetworkErrors.SUCCESS) {
                updateState {
                    it.copy(
                        likes = it.likes + title,
                        isLoading = false
                    )
                }
            } else {
                updateState {
                    it.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                sendRetrySnackbar(
                    label = "Ошибка в добавлении избранного: ${response.label!!}",
                    action = { fetchLikesAmount() }
                )
            }
        }
    }

    private fun removeLike(title: Data) {
        viewModelScope.launch(dispatcherIo) {
            updateState { it.copy(isLoading = true) }

            val response = likesRepository.removeLike(
                _authState.value.sessionToken!!,
                title.id
            )

            if (response.error == NetworkErrors.SUCCESS) {
                updateState {
                    it.copy(
                        likes = it.likes - title,
                        isLoading = false
                    )
                }
            } else {
                updateState {
                    it.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                sendRetrySnackbar(
                    label = "Ошибка в удалении избранного: ${response.label!!}",
                    action = { fetchLikesAmount() }
                )
            }
        }
    }

    fun sendIntent(intent: AuthIntent) {
        when (intent) {
            // Tokens
            is AuthIntent.GetSessionToken -> getSessionToken()
            is AuthIntent.ClearSessionToken -> clearSessionToken()

            // Ui state
            AuthIntent.ChangeIsAuthBsOpened -> updateState { it.copy(isAuthBSOpened = !it.isAuthBSOpened) }
            is AuthIntent.ChangePassword -> updateState { it.copy(password = intent.password) }
            is AuthIntent.ChangeEmail -> updateState { it.copy(email = intent.email) }
            AuthIntent.ChangeIsPasswordVisible -> updateState { it.copy(isPasswordVisible = !it.isPasswordVisible) }

            // Likes
            is AuthIntent.AddLike -> addLike(intent.title)
            is AuthIntent.RemoveLike -> removeLike(intent.title)
        }
    }

    init {
        observeSessionToken()
    }
}