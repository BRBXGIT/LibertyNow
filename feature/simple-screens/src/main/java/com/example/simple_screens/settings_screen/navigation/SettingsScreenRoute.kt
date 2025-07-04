package com.example.simple_screens.settings_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen() = composable<SettingsScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    SettingsScreen()
}