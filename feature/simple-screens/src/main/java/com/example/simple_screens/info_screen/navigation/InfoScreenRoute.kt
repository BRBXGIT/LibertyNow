package com.example.simple_screens.info_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.simple_screens.info_screen.screen.InfoScreen
import kotlinx.serialization.Serializable

@Serializable
object InfoScreenRoute

fun NavGraphBuilder.infoScreen(
    navController: NavController
) = composable<InfoScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    InfoScreen(navController)
}