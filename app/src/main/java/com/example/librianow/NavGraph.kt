package com.example.librianow

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.navbar_screens.home_screen.navigation.homeScreen
import com.example.onboarding_screen.navigation.onBoardingScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onBoardingScreen()

        homeScreen()
    }
}