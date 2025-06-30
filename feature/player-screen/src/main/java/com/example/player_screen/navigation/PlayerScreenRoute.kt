package com.example.player_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.design_system.theme.CommonConstants
import com.example.player_screen.screen.PlayerScreen
import kotlinx.serialization.Serializable

@Serializable
data class PlayerScreenRoute(
    val currentItemId: Int,
    val gsonLinks: String
)

fun NavGraphBuilder.playerScreen() = composable<PlayerScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val currentItemId = it.toRoute<PlayerScreenRoute>().currentItemId
    val gsonLinks = it.toRoute<PlayerScreenRoute>().gsonLinks

    PlayerScreen(
        currentAnimeId = currentItemId,
        gsonLinks = gsonLinks
    )
}