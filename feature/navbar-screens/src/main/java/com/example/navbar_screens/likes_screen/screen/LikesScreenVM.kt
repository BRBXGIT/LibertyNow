package com.example.navbar_screens.likes_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LikesScreenVM: ViewModel() {

    private val _likesScreenState = MutableStateFlow(LikesScreenState())
    val likesScreenState = _likesScreenState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        LikesScreenState()
    )

    // === Private helpers ===
    private fun updateState(transform: (LikesScreenState) -> LikesScreenState) {
        _likesScreenState.update(transform)
    }

    // === Intents ===
    fun sendIntent(intent: LikesScreenIntent) {
        when (intent) {
            LikesScreenIntent.ChangeIsSearching -> updateState { it.copy(isSearching = !it.isSearching) }

            is LikesScreenIntent.ChangeQuery -> updateState { it.copy(query = intent.query) }
        }
    }
}