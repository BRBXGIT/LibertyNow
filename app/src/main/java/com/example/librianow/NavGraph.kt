package com.example.librianow

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.anime_screen.navigation.animeScreen
import com.example.common.CommonVM
import com.example.navbar_screens.home_screen.navigation.homeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.navbar_screens.likes_screen.navigation.likesScreen
import com.example.navbar_screens.search_screen.navigation.searchScreen
import com.example.navbar_screens.settings_screen.navigation.settingsScreen
import com.example.onboarding_screen.navigation.onBoardingScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    // Initialize here to don't recompose values
    val commonVM = hiltViewModel<CommonVM>()
    val commonState = commonVM.commonState.collectAsStateWithLifecycle().value

    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    NavHost(
        navController = navController,
        startDestination = AnimeScreenRoute(9934) // TODO change to start destination
    ) {
        onBoardingScreen()

        homeScreen(
            homeScreenVM = homeScreenVM,
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        likesScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        searchScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        settingsScreen(
            commonVM = commonVM,
            commonState = commonState,
            navController = navController
        )

        animeScreen()
    }
}