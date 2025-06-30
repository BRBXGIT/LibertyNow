package com.example.anime_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.anime_screen.screen.AnimeScreen
import com.example.anime_screen.screen.AnimeScreenVM
import com.example.common.auth.AuthVM
import com.example.design_system.theme.CommonConstants
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScreenRoute(
    val animeId: Int
)

fun NavGraphBuilder.animeScreen(
    navController: NavController,
    authVM: AuthVM
) = composable<AnimeScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val animeId = it.toRoute<AnimeScreenRoute>().animeId
    val animeScreenVM = hiltViewModel<AnimeScreenVM>()

    AnimeScreen(
        animeId = animeId,
        viewModel = animeScreenVM,
        navController = navController,
        authVM = authVM,
    )
}