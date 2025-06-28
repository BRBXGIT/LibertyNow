package com.example.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommonVM @Inject constructor(

): ViewModel() {
    private val _commonState = MutableStateFlow(CommonState())
    val commonState = _commonState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
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