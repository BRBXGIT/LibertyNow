package com.example.onboarding_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.onboarding_screen.screen.OnBoardingScreen
import com.example.onboarding_screen.screen.OnBoardingScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object OnBoardingScreenRoute

fun NavGraphBuilder.onBoardingScreen() = composable<OnBoardingScreenRoute> {
    val onBoardingScreenVM = hiltViewModel<OnBoardingScreenVM>()

    OnBoardingScreen(
        viewModel = onBoardingScreenVM
    )
}