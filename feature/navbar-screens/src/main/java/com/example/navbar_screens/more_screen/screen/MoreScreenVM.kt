package com.example.navbar_screens.more_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MoreScreenVM: ViewModel() {
    private val _moreScreenState = MutableStateFlow(MoreScreenState())
    val moreScreenState = _moreScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        MoreScreenState()
    )

    private fun updateScreenState(state: MoreScreenState) {
        _moreScreenState.value = state
    }

    fun sendIntent(intent: MoreScreenIntent) {
        when(intent) {
            is MoreScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }
}