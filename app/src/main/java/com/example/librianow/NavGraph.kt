package com.example.librianow

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.anime_screen.navigation.animeScreen
import com.example.common.auth.AuthVM
import com.example.common.common.CommonVM
import com.example.navbar_screens.home_screen.navigation.homeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.navbar_screens.likes_screen.navigation.likesScreen
import com.example.navbar_screens.likes_screen.screen.LikesScreenVM
import com.example.navbar_screens.search_screen.navigation.searchScreen
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import com.example.navbar_screens.settings_screen.navigation.settingsScreen
import com.example.onboarding_screen.navigation.onBoardingScreen
import com.example.player_screen.navigation.playerScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    // Initialize here to don't recompose values
    val commonVM = hiltViewModel<CommonVM>()
    val authVM = hiltViewModel<AuthVM>()

    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    val searchScreenVM = hiltViewModel<SearchScreenVM>()
    val likesScreenVM = hiltViewModel<LikesScreenVM>()
    NavHost(
        navController = navController,
        startDestination = AnimeScreenRoute(9934)
    ) {
        onBoardingScreen()

        homeScreen(
            homeScreenVM = homeScreenVM,
            commonVM = commonVM,
            navController = navController
        )

        likesScreen(
            likesScreenVM = likesScreenVM,
            commonVM = commonVM,
            navController = navController,
            authVM = authVM
        )

        searchScreen(
            searchScreenVM = searchScreenVM,
            commonVM = commonVM,
            navController = navController
        )

        settingsScreen(
            commonVM = commonVM,
            navController = navController
        )

        animeScreen(
            navController = navController,
            authVM = authVM
        )

        playerScreen(
            navController = navController
        )
    }
}