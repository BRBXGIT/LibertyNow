package com.example.onboarding_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.onboarding_screen.screen.OnBoardingScreen
import kotlinx.serialization.Serializable

@Serializable
data object OnBoardingScreenRoute

fun NavGraphBuilder.onBoardingScreen() = composable<OnBoardingScreenRoute> {
    OnBoardingScreen()
}