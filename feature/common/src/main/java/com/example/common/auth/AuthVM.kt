package com.example.common.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.AuthRepo
import com.example.data.domain.LikesRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.models.likes_amount_response.LikesAmountResponse
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import com.example.network.common.titles_list_response.Item0
import com.example.network.common.titles_list_response.TitlesListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
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

    private fun updateAuthState(state: AuthState) {
        _authState.value = state
    }

    private fun fetchSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(
                    isLogged = authRepository.loggingState.first(),
                    sessionToken = authRepository.userSessionToken.first()
                )
            }
            if (_authState.value.isLogged is LoggingState.LoggedIn) {
                fetchLikesAmount()
            }
        }
    }

    private fun getSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(
                    isLoading = true,
                    incorrectEmail = false,
                    incorrectPassword = false,
                    isAuthBSOpened = false
                )
            }

            val response = authRepository.getSessionToken(_authState.value.email, _authState.value.password)
            when (response.error) {
                NetworkErrors.SUCCESS -> {
                    authRepository.saveUserSessionToken((response.response as SessionTokenResponse).sessionId)
                    fetchSessionToken()
                }
                NetworkErrors.INCORRECT_PASSWORD -> {
                    _authState.update { state ->
                        state.copy(
                            incorrectPassword = true,
                            isAuthBSOpened = true
                        )
                    }
                }
                NetworkErrors.INCORRECT_EMAIL -> {
                    _authState.update { state ->
                        state.copy(
                            incorrectEmail = true,
                            isAuthBSOpened = true
                        )
                    }
                }
                else -> {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = response.label!!,
                            action = SnackbarAction(
                                name = "Retry",
                                action = { getSessionToken() }
                            )
                        )
                    )
                }
            }

            _authState.update { state ->
                state.copy(isLoading = false)
            }
        }
    }

    private fun fetchLikesAmount() {
        viewModelScope.launch(dispatcherIo) {
            val response = likesRepository.getLikesAmount(_authState.value.sessionToken!!)
            _authState.update { state ->
                state.copy(
                    likesError = false,
                    isLoading = true
                )
            }

            if (response.error == NetworkErrors.SUCCESS) {
                _authState.update { state ->
                    state.copy(
                        likesAmount = (response.response as LikesAmountResponse).pagination.totalItems,
                        isLoading = false
                    )
                }
                fetchLikes()
            } else {
                _authState.update { state ->
                    state.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Проблема в получении избранных: ${response.label!!}",
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchLikesAmount() }
                        )
                    )
                )
            }
        }
    }

    private fun fetchLikes() {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(isLoading = true)
            }

            val response = likesRepository.getLikes(
                _authState.value.sessionToken!!,
                _authState.value.likesAmount
            )

            if (response.error == NetworkErrors.SUCCESS) {
                _authState.update { state ->
                    state.copy(
                        likes = (response.response as TitlesListResponse).list,
                        isLoading = false
                    )
                }
            } else {
                _authState.update { state ->
                    state.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Проблема в получении избранных: ${response.label!!}",
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchLikesAmount() }
                        )
                    )
                )
            }
        }
    }

    private fun addLike(title: Item0) {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(isLoading = true)
            }

            val response = likesRepository.addLike(
                _authState.value.sessionToken!!,
                title.id
            )

            if (response.error == NetworkErrors.SUCCESS) {
                _authState.update { state ->
                    state.copy(
                        likes = state.likes + title,
                        isLoading = false
                    )
                }
            } else {
                _authState.update { state ->
                    state.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Ошибка в добавлении избранного: ${response.label!!}",
                        action = SnackbarAction(
                            name = "Retry",
                            action = { addLike(title) }
                        )
                    )
                )
            }
        }
    }

    private fun removeLike(title: Item0) {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(isLoading = true)
            }

            val response = likesRepository.removeLike(
                _authState.value.sessionToken!!,
                title.id
            )

            if (response.error == NetworkErrors.SUCCESS) {
                _authState.update { state ->
                    state.copy(
                        likes = state.likes - title,
                        isLoading = false
                    )
                }
            } else {
                _authState.update { state ->
                    state.copy(
                        likesError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = "Ошибка в добавлении избранного: ${response.label!!}",
                        action = SnackbarAction(
                            name = "Retry",
                            action = { addLike(title) }
                        )
                    )
                )
            }
        }
    }

    fun sendIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.GetSessionToken -> getSessionToken()
            is AuthIntent.UpdateAuthState -> updateAuthState(intent.state)
            is AuthIntent.AddLike -> addLike(intent.title)
            is AuthIntent.RemoveLike -> removeLike(intent.title)
        }
    }

    init {
        fetchSessionToken()
    }
}