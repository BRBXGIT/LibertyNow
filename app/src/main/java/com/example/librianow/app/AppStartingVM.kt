package com.example.librianow.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.OnBoardingRepo
import com.example.data.domain.ThemeRepo
import com.example.local.datastore.app_theme.ThemeState
import com.example.local.datastore.onboarding.OnBoardingState
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
                themeRepo.colorSystem,
                themeRepo.useExpressive
            ) { values: Array<Any?> ->
                AppStartingState(
                    onBoardingState = values[0] as OnBoardingState,
                    themeState = values[1] as ThemeState,
                    colorSystemState = values[2] as ThemeState,
                    theme = values[3] as? String ?: "default",
                    colorSystem = values[4] as? String ?: "default",
                    useExpressive = values[5] as? Boolean ?: false
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