package com.example.navbar_screens.likes_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.AuthRepo
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
class LikesScreenVM @Inject constructor(
    private val authRepository: AuthRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _likesScreenState = MutableStateFlow(LikesScreenState())
    val likesScreenState = _likesScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        LikesScreenState()
    )

    private fun fetchSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            _likesScreenState.update { state ->
                state.copy(
                    isUserLoggedIn = authRepository.authState.first(),
                    sessionToken = authRepository.userSessionToken.first()
                )
            }
        }
    }

    private fun updateScreenState(state: LikesScreenState) {
        _likesScreenState.value = state
    }

    fun sendIntent(intent: LikesScreenIntent) {
        when (intent) {
            is LikesScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }

    init {
        fetchSessionToken()
    }
}