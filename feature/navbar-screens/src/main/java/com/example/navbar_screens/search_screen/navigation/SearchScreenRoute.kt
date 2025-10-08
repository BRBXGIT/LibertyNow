package com.example.navbar_screens.search_screen.navigation

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
import com.example.navbar_screens.search_screen.screen.SearchScreen
import com.example.navbar_screens.search_screen.screen.SearchScreenVM
import kotlinx.serialization.Serializable

@Serializable
object SearchScreenRoute

fun NavGraphBuilder.searchScreen(
    searchScreenVM: SearchScreenVM,
    commonVM: CommonVM,
    navController: NavController,
    useExpressive: Boolean
) = composable<SearchScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val searchScreenState by searchScreenVM.searchScreenState.collectAsStateWithLifecycle()
    val commonState by commonVM.commonState.collectAsStateWithLifecycle()

    val animeByFilters = searchScreenVM.animeByFilters.collectAsLazyPagingItems()

    SearchScreen(
        screenState = searchScreenState,
        commonState = commonState,
        animeByFilters = animeByFilters,
        onIntent = searchScreenVM::sendIntent,
        onCommonIntent = commonVM::sendIntent,
        useExpressive = useExpressive,
        navController = navController
    )
}