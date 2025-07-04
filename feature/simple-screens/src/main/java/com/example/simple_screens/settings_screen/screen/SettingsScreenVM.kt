package com.example.simple_screens.settings_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.PlayerFeaturesRepo
import com.example.data.domain.ThemeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenVM @Inject constructor(
    private val themeRepo: ThemeRepo,
    private val playerFeaturesRepo: PlayerFeaturesRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _settingsScreenState = MutableStateFlow(SettingsScreenState())
    val settingsScreenState = _settingsScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SettingsScreenState()
    )

    private fun observePlayerFeatures() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                playerFeaturesRepo.videoQuality,
                playerFeaturesRepo.autoPlay,
                playerFeaturesRepo.isCropped,
                playerFeaturesRepo.showSkipOpeningButton
            ) { videoQuality, autoPlay, isCropped, showSkipOpeningButton ->
                PlayerFeaturePrefs(
                    videoQuality = videoQuality ?: 1080,
                    autoPlay = autoPlay == true,
                    isCropped = isCropped == true,
                    showSkipOpeningButton = showSkipOpeningButton == true
                )
            }.collect { prefs ->
                _settingsScreenState.update { state ->
                    state.copy(
                        quality = prefs.videoQuality,
                        autoPlay = prefs.autoPlay,
                        isCropped = prefs.isCropped,
                        showSkipOpeningButton = prefs.showSkipOpeningButton
                    )
                }
            }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch(dispatcherIo) {
            combine(
                themeRepo.theme,
                themeRepo.colorSystem
            ) { theme, colorSystem ->
                theme to colorSystem
            }.collect { (theme, colorSystem) ->
                _settingsScreenState.update { state ->
                    state.copy(
                        theme = theme ?: "default",
                        colorSystem = colorSystem ?: "default"
                    )
                }
            }
        }
    }

    init {
        observePlayerFeatures()
        observeTheme()
    }
}