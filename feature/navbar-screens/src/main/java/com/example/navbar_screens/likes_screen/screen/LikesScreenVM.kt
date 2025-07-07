package com.example.navbar_screens.likes_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class LikesScreenVM: ViewModel() {

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