package com.example.common.auth

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
class AuthVM @Inject constructor(
    private val repository: AuthRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AuthState()
    )

    private fun fetchSessionToken() {
        viewModelScope.launch(dispatcherIo) {
            _authState.update { state ->
                state.copy(
                    isLogged = repository.authState.first(),
                    sessionToken = repository.userSessionToken.first()
                )
            }
        }
    }
}