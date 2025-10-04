package com.example.navbar_screens.more_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MoreScreenVM: ViewModel() {
    private val _moreScreenState = MutableStateFlow(MoreScreenState())
    val moreScreenState = _moreScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        MoreScreenState()
    )

    // === Private helpers ===
    private fun updateState(transform: (MoreScreenState) -> MoreScreenState) {
        _moreScreenState.update(transform)
    }

    // === Intents ===
    fun sendIntent(intent: MoreScreenIntent) {
        when(intent) {
            MoreScreenIntent.ChangeIsQuitAdVisible -> updateState { it.copy(isQuitADVisible = !it.isQuitADVisible) }
        }
    }
}