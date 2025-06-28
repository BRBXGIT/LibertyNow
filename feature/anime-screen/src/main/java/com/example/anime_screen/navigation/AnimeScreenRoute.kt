package com.example.anime_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.anime_screen.screen.AnimeScreen
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScreenRoute(
    val animeId: Int
)

fun NavGraphBuilder.animeScreen() = composable<AnimeScreenRoute> {
    val animeId = it.toRoute<AnimeScreenRoute>().animeId

    AnimeScreen(
        animeId = animeId
    )
}