package com.example.navbar_screens.search_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.search_screen.screen.SearchScreen
import kotlinx.serialization.Serializable

@Serializable
object SearchScreenRoute

fun NavGraphBuilder.searchScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<SearchScreenRoute> {
    SearchScreen(
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}