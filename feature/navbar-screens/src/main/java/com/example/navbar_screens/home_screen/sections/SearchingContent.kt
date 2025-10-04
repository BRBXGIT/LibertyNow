package com.example.navbar_screens.home_screen.sections

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.design_system.cards.AnimeCard
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.theme.DesignUtils
import com.example.navbar_screens.common.AnimeLVGContainer
import com.example.navbar_screens.home_screen.screen.HomeScreenState
import com.example.network.common.models.anime_list_with_pagination_response.Data

@Composable
fun BoxScope.SearchingContent(
    screenState: HomeScreenState,
    titlesByQuery: LazyPagingItems<Data>,
    onCardClick: (Int) -> Unit
) {
    if (screenState.query == "") {
        NothingHereSection()
    } else if (screenState.isAnimeByQueryError) {
        ErrorSection(modifier = Modifier.align(Alignment.Center))
    } else {
        AnimeLVGContainer {
            items(titlesByQuery.itemCount, key = { it }) { index ->
                val anime = titlesByQuery[index]

                anime?.let {
                    AnimeCard(
                        posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                        genresString = anime.genres.joinToString(", ") { it.name },
                        title = anime.name.main,
                        modifier = Modifier.animateItem(),
                        onCardClick = { onCardClick(anime.id) },
                    )
                }
            }
        }
    }
}