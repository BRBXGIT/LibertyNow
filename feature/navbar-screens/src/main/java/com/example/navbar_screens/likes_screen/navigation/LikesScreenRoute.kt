package com.example.navbar_screens.likes_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.CommonState
import com.example.common.CommonVM
import com.example.navbar_screens.likes_screen.screen.LikesScreen
import kotlinx.serialization.Serializable

@Serializable
data object LikesScreenRoute

fun NavGraphBuilder.likesScreen(
    commonVM: CommonVM,
    commonState: CommonState,
    navController: NavController
) = composable<LikesScreenRoute> {
    LikesScreen(
        commonVM = commonVM,
        commonState = commonState,
        navController = navController
    )
}