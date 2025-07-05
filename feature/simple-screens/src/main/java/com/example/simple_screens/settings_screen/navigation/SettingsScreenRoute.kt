package com.example.simple_screens.settings_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.settings_screen.screen.SettingsScreen
import com.example.simple_screens.settings_screen.screen.SettingsScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    navController: NavController
) = composable<SettingsScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val settingsScreenVM = hiltViewModel<SettingsScreenVM>()

    SettingsScreen(
        viewModel = settingsScreenVM,
        navController = navController
    )
}