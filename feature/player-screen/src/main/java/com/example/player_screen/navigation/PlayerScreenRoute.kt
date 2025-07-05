package com.example.player_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.design_system.theme.CommonConstants
import com.example.player_screen.screen.PlayerScreen
import com.example.player_screen.screen.PlayerScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class PlayerScreenRoute(
    val animeId: Int,
    val currentEpisodeId: Int,
    val gsonLinks: String,
    val host: String
)

fun NavGraphBuilder.playerScreen(
    navController: NavController
) = composable<PlayerScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val currentEpisodeId = it.toRoute<PlayerScreenRoute>().currentEpisodeId
    val gsonLinks = it.toRoute<PlayerScreenRoute>().gsonLinks
    val host = it.toRoute<PlayerScreenRoute>().host
    val animeId = it.toRoute<PlayerScreenRoute>().animeId

    val playerScreenVM = hiltViewModel<PlayerScreenVM>()
    PlayerScreen(
        currentEpisodeId = currentEpisodeId,
        gsonLinks = gsonLinks,
        viewModel = playerScreenVM,
        host = host,
        navController = navController,
        animeId = animeId
    )
}