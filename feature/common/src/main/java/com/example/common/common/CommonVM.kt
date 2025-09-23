package com.example.common.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CommonVM: ViewModel() {
    private val _commonState = MutableStateFlow(CommonState())
    val commonState = _commonState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CommonState()
    )

    // === Private helpers ===
    private fun updateState(transform: (CommonState) -> CommonState) {
        _commonState.update(transform)
    }

    fun sendIntent(intent: CommonIntent) {
        when (intent) {
            is CommonIntent.ChangeNavIndex -> updateState { it.copy(selectedNavBarIndex = intent.index) }
        }
    }
}