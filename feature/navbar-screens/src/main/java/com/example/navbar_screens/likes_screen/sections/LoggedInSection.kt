package com.example.navbar_screens.likes_screen.sections

import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.common.auth.AuthState
import com.example.design_system.cards.AnimeCard
import com.example.design_system.theme.DesignUtils
import com.example.navbar_screens.common.AnimeLVGContainer
import com.example.navbar_screens.likes_screen.screen.LikesScreenState

@Composable
fun LoggedInSection(
    screenState: LikesScreenState,
    authState: AuthState,
    navController: NavController
) {
    if (screenState.isSearching) {
        val filteredLikes = authState.likes.filter {
            it.name.main.contains(screenState.query, ignoreCase = true)
        }
        AnimeLVGContainer {
            items(filteredLikes.reversed()) { like ->
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + like.poster.optimized.preview,
                    genresString = like.genres.joinToString(", ") { it.name },
                    title = like.name.main,
                    modifier = Modifier.animateItem(),
                    onCardClick = { navController.navigate(AnimeScreenRoute(like.id)) },
                )
            }
        }
    } else {
        AnimeLVGContainer {
            items(authState.likes.reversed()) { like ->
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + like.poster.optimized.preview,
                    genresString = like.genres.joinToString(", ") { it.name },
                    title = like.name.main,
                    modifier = Modifier.animateItem(),
                    onCardClick = { navController.navigate(AnimeScreenRoute(like.id)) },
                )
            }
        }
    }
}