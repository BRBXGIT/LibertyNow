package com.example.navbar_screens.search_screen.sections

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.anime_screen.navigation.AnimeScreenRoute
import com.example.design_system.cards.AnimeCard
import com.example.design_system.theme.DesignUtils
import com.example.navbar_screens.common.AnimeLVGContainer
import com.example.network.common.models.anime_list_with_pagination_response.Data

@Composable
fun AnimeByFiltersSection(
    animeByFilters: LazyPagingItems<Data>,
    navController: NavController
) {
    AnimeLVGContainer {
        items(animeByFilters.itemCount) { index ->
            val anime = animeByFilters[index]

            anime?.let {
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.optimized.preview,
                    genresString = anime.genres.joinToString(", ") { it.name },
                    title = anime.name.main,
                    onCardClick = { navController.navigate(AnimeScreenRoute(it.id)) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}