package com.example.navbar_screens.home_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbar_screens.home_screen.screen.HomeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

fun NavGraphBuilder.homeScreen(
    homeScreenVM: HomeScreenVM
) = composable<HomeScreenRoute> {
    HomeScreen(
        viewModel = homeScreenVM
    )
}