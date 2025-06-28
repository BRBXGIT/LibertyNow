package com.example.librianow

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.anime_screen.navigation.animeScreen
import com.example.navbar_screens.home_screen.navigation.homeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.onboarding_screen.navigation.onBoardingScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        onBoardingScreen()

        homeScreen(
            homeScreenVM = homeScreenVM,
            navController = navController
        )

        animeScreen()
    }
}