package com.example.navbar_screens.more_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.common.auth.AuthVM
import com.example.common.common.CommonVM
import com.example.design_system.theme.CommonConstants
import com.example.navbar_screens.more_screen.screen.MoreScreen
import com.example.navbar_screens.more_screen.screen.MoreScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object MoreScreenRoute

fun NavGraphBuilder.moreScreen(
    commonVM: CommonVM,
    navController: NavController,
    moreScreenVM: MoreScreenVM,
    authVM: AuthVM
) = composable<MoreScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    MoreScreen(
        commonVM = commonVM,
        navController = navController,
        viewModel = moreScreenVM,
        authVM = authVM
    )
}