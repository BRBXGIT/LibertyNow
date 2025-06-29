package com.example.navbar_screens.search_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.design_system.theme.CommonConstants
import com.example.navbar_screens.search_screen.screen.SearchScreen
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import kotlinx.serialization.Serializable

@Serializable
object SearchScreenRoute

fun NavGraphBuilder.searchScreen(
    searchScreenVM: SearchScreenVM,
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<SearchScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    SearchScreen(
        viewModel = searchScreenVM,
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}