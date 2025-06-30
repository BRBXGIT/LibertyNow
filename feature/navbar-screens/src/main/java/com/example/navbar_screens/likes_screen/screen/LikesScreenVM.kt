package com.example.navbar_screens.likes_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LikesScreenVM @Inject constructor(
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {

    private val _likesScreenState = MutableStateFlow(LikesScreenState())
    val likesScreenState = _likesScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        LikesScreenState()
    )

    private fun updateScreenState(state: LikesScreenState) {
        _likesScreenState.value = state
    }

    fun sendIntent(intent: LikesScreenIntent) {
        when (intent) {
            is LikesScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }
}