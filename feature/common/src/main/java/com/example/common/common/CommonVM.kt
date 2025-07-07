package com.example.common.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class CommonVM: ViewModel() {
    private val _commonState = MutableStateFlow(CommonState())
    val commonState = _commonState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        CommonState()
    )

    private fun updateState(state: CommonState) {
        _commonState.value = state
    }

    fun sendIntent(intent: CommonIntent) {
        when (intent) {
            is CommonIntent.UpdateState -> updateState(intent.state)
        }
    }
}