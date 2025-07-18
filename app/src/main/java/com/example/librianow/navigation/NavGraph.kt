package com.example.librianow.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.anime_screen.navigation.animeScreen
import com.example.common.auth.AuthVM
import com.example.common.common.CommonVM
import com.example.navbar_screens.home_screen.navigation.homeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import com.example.navbar_screens.likes_screen.navigation.likesScreen
import com.example.navbar_screens.likes_screen.screen.LikesScreenVM
import com.example.navbar_screens.lists_screen.navigation.listsScreen
import com.example.navbar_screens.lists_screen.screen.ListsScreenVM
import com.example.navbar_screens.more_screen.navigation.moreScreen
import com.example.navbar_screens.more_screen.screen.MoreScreenVM
import com.example.navbar_screens.search_screen.navigation.searchScreen
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import com.example.onboarding_screen.navigation.onBoardingScreen
import com.example.player_screen.navigation.playerScreen
import com.example.simple_screens.info_screen.navigation.infoScreen
import com.example.simple_screens.project_team_screen.navigation.projectTeamScreen
import com.example.simple_screens.settings_screen.navigation.settingsScreen
import com.example.simple_screens.support_screen.navigation.supportScreen

@Composable
fun NavGraph(
    startDestination: Any
) {
    val navController = rememberNavController()

    // Initialize here to don't recompose values
    val commonVM = viewModel<CommonVM>()
    val authVM = hiltViewModel<AuthVM>()

    val homeScreenVM = hiltViewModel<HomeScreenVM>()
    val searchScreenVM = hiltViewModel<SearchScreenVM>()
    val likesScreenVM = viewModel<LikesScreenVM>()
    val listsScreenVM = hiltViewModel<ListsScreenVM>()
    val moreScreenVM = viewModel<MoreScreenVM>()
    NavHost(
        navController = navController,
        startDestination = startDestination
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

        listsScreen(
            commonVM = commonVM,
            listsScreenVM = listsScreenVM,
            navController = navController,
        )

        searchScreen(
            searchScreenVM = searchScreenVM,
            commonVM = commonVM,
            navController = navController
        )

        moreScreen(
            commonVM = commonVM,
            navController = navController,
            moreScreenVM = moreScreenVM,
            authVM = authVM
        )

        animeScreen(navController, authVM)

        playerScreen(navController)

        projectTeamScreen(navController)

        supportScreen(navController)

        settingsScreen(navController)

        infoScreen(navController)
    }
}