package com.example.navbar_screens.settings_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.common.CommonVM
import com.example.design_system.theme.CommonConstants
import com.example.navbar_screens.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    commonVM: CommonVM,
    navController: NavController
) = composable<SettingsScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    SettingsScreen(
        commonVM = commonVM,
        navController = navController
    )
}