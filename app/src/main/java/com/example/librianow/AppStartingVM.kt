package com.example.librianow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.OnBoardingRepo
import com.example.data.domain.ThemeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppStartingVM @Inject constructor(
    private val onBoardingRepo: OnBoardingRepo,
    private val themeRepo: ThemeRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _appStartingState = MutableStateFlow(AppStartingState())
    val appStartingState = _appStartingState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppStartingState()
    )

    private fun observeStates() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                onBoardingRepo.onBoardingState,
                themeRepo.themeState,
                themeRepo.colorSystemState,
                themeRepo.theme,
                themeRepo.colorSystem
            ) { onBoardingState, themeState, colorSystemState, theme, colorSystem ->
                AppStartingState(
                    onBoardingState = onBoardingState,
                    themeState = themeState,
                    colorSystemState = colorSystemState,
                    theme = theme ?: "default",
                    colorSystem = colorSystem ?: "default"
                )
            }.collect { combinedState ->
                _appStartingState.value = combinedState
            }
        }
    }

    init {
        observeStates()
    }
}