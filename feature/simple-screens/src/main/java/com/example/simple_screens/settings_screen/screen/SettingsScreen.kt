package com.example.simple_screens.settings_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.theme.mColors
import com.example.simple_screens.common.SimpleTopBar
import com.example.simple_screens.settings_screen.sections.PlayerSettingsItemType
import com.example.simple_screens.settings_screen.sections.SettingsLCSection
import com.example.simple_screens.settings_screen.sections.VideoQualityBS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenVM,
    navController: NavController
) {
    val screenState by viewModel.settingsScreenState.collectAsStateWithLifecycle()

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Настройки",
                onBackClick = { navController.navigateUp() },
                scrollBehavior = topBarScrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        if (screenState.isQualityBSVisible) {
            VideoQualityBS(
                onDismissRequest = {
                    viewModel.sendIntent(
                        SettingsScreenIntent.UpdateScreenState(
                            screenState.copy(isQualityBSVisible = false)
                        )
                    )
                },
                onSetQualityClick = {
                    viewModel.sendIntent(
                        SettingsScreenIntent.SetPlayerFeature(
                            type = PlayerSettingsItemType.VideoQuality,
                            quality = it
                        )
                    )
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mColors.background)
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            SettingsLCSection(
                chosenTheme = screenState.theme,
                chosenColorSystem = screenState.colorSystem,
                videoQuality = screenState.videoQuality,
                autoPlay = screenState.autoPlay,
                showSkipOpeningButton = screenState.showSkipOpeningButton,
                isCropped = screenState.isCropped,
                onColorSystemChange = {
                    viewModel.sendIntent(SettingsScreenIntent.SetColorSystem(it))
                },
                onThemeChange = {
                    viewModel.sendIntent(SettingsScreenIntent.SetTheme(it))
                },
                bottomPadding = innerPadding.calculateBottomPadding(),
                onCheckChange = {
                    when(it) {
                        PlayerSettingsItemType.VideoQuality -> {
                            viewModel.sendIntent(
                                SettingsScreenIntent.UpdateScreenState(
                                    screenState.copy(isQualityBSVisible = true)
                                )
                            )
                        }
                        PlayerSettingsItemType.ShowSkipOpeningButton -> {
                            viewModel.sendIntent(
                                SettingsScreenIntent.SetPlayerFeature(
                                    type = PlayerSettingsItemType.ShowSkipOpeningButton
                                )
                            )
                        }
                        PlayerSettingsItemType.Crop -> {
                            viewModel.sendIntent(
                                SettingsScreenIntent.SetPlayerFeature(
                                    type = PlayerSettingsItemType.Crop
                                )
                            )
                        }
                        PlayerSettingsItemType.AutoPlay -> {
                            viewModel.sendIntent(
                                SettingsScreenIntent.SetPlayerFeature(
                                    type = PlayerSettingsItemType.AutoPlay
                                )
                            )
                        }
                    }
                },
            )
        }
    }
}