package com.example.simple_screens.support_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.support_screen.screen.SupportScreen
import kotlinx.serialization.Serializable

@Serializable
data object SupportScreenRoute

fun NavGraphBuilder.supportScreen(
    navController: NavController
) = composable<SupportScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    SupportScreen(navController)
}