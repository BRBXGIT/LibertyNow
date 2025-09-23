package com.example.navbar_screens.home_screen.sections

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.design_system.cards.AnimeCard
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.theme.DesignUtils
import com.example.navbar_screens.common.AnimeLVGContainer
import com.example.navbar_screens.home_screen.screen.HomeScreenIntent
import com.example.navbar_screens.home_screen.screen.HomeScreenState
import com.example.navbar_screens.home_screen.screen.HomeScreenVM

@Composable
fun BoxScope.AnimeUpdatesContent(
    screenState: HomeScreenState,
    navController: NavController,
    viewModel: HomeScreenVM
) {
    if (screenState.isError) {
        ErrorSection(modifier = Modifier.align(Alignment.Center))
    } else {
        AnimeLVGContainer {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                RandomAnimeButton(
                    onClick = {
                        viewModel.sendIntent(
                            HomeScreenIntent.FetchRandomTitle(
                                onComplete = {
                                    navController.navigate(AnimeScreenRoute(it))
                                }
                            )
                        )
                    }
                )
            }

            items(screenState.titlesUpdates, key = { it.id }) { anime ->
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                    genresString = anime.genres.joinToString(", ") { it.name },
                    title = anime.name.main,
                    modifier = Modifier.animateItem(),
                    onCardClick = {  navController.navigate(AnimeScreenRoute(anime.id)) },
                )
            }
        }
    }
}