package com.example.navbar_screens.likes_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.auth.AuthVM
import com.example.common.common.CommonVM
import com.example.design_system.theme.CommonConstants
import com.example.navbar_screens.likes_screen.screen.LikesScreen
import com.example.navbar_screens.likes_screen.screen.LikesScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object LikesScreenRoute

fun NavGraphBuilder.likesScreen(
    likesScreenVM: LikesScreenVM,
    commonVM: CommonVM,
    authVM: AuthVM,
    navController: NavController
) = composable<LikesScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    LikesScreen(
        viewModel = likesScreenVM,
        commonVM = commonVM,
        authVM = authVM,
        navController = navController
    )
}