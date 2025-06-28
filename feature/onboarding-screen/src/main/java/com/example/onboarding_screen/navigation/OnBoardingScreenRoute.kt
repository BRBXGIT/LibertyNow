package com.example.onboarding_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.design_system.theme.CommonConstants
import com.example.onboarding_screen.screen.OnBoardingScreen
import com.example.onboarding_screen.screen.OnBoardingScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object OnBoardingScreenRoute

fun NavGraphBuilder.onBoardingScreen() = composable<OnBoardingScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

    OnBoardingScreen(
        viewModel = onBoardingScreenVM
    )
}