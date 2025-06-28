package com.example.navbar_screens.home_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.home_screen.screen.HomeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

fun NavGraphBuilder.homeScreen(
    homeScreenVM: HomeScreenVM,
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<HomeScreenRoute> {
    HomeScreen(
        viewModel = homeScreenVM,
        navController = navController,
        commonVM = commonVM,
        commonState = commonState
    )
}