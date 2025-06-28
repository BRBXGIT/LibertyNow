package com.example.anime_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.anime_screen.screen.AnimeScreen
import com.example.design_system.theme.CommonConstants
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScreenRoute(
    val animeId: Int
)

fun NavGraphBuilder.animeScreen() = composable<AnimeScreenRoute>(
    enterTransition = { fadeIn(tween(CommonConstants.ANIMATION_DURATION)) },
    exitTransition = { fadeOut(tween(CommonConstants.ANIMATION_DURATION)) }
) {
    val animeId = it.toRoute<AnimeScreenRoute>().animeId

    AnimeScreen(
        animeId = animeId
    )
}