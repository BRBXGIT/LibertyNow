package com.example.navbar_screens.home_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.common.CommonVM
import com.example.design_system.theme.CommonConstants
import com.example.navbar_screens.home_screen.screen.HomeScreen
import com.example.navbar_screens.home_screen.screen.HomeScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

fun NavGraphBuilder.homeScreen(
    homeScreenVM: HomeScreenVM,
    commonVM: CommonVM,
    navController: NavController,
    useExpressive: Boolean
) = composable<HomeScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val titlesByQuery = homeScreenVM.titlesByQuery.collectAsLazyPagingItems()

    val commonState by commonVM.commonState.collectAsStateWithLifecycle()
    val homeScreenState by homeScreenVM.homeScreenState.collectAsStateWithLifecycle()

    HomeScreen(
        navController = navController,
        useExpressive = useExpressive,
        commonState = commonState,
        screenState = homeScreenState,
        titlesByQuery = titlesByQuery,
        onIntent = homeScreenVM::sendIntent,
        onCommonIntent = commonVM::sendIntent,
    )
}