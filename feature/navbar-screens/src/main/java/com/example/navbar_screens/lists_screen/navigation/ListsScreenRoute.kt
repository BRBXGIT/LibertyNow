package com.example.navbar_screens.lists_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.common.CommonVM
import com.example.navbar_screens.lists_screen.screen.ListsScreen
import com.example.navbar_screens.lists_screen.screen.ListsScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object ListsScreenRoute

fun NavGraphBuilder.listsScreen(
    listsScreenVM: ListsScreenVM,
    commonVM: CommonVM,
    navController: NavController
) = composable<ListsScreenRoute> {
    ListsScreen(
        commonVM = commonVM,
        navController = navController,
        viewModel = listsScreenVM
    )
}