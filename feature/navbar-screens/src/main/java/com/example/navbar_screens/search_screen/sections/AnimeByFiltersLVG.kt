package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.cards.AnimeCard
import com.example.design_system.theme.DesignUtils
import com.example.design_system.theme.LibriaNowTheme
import com.example.network.common.models.Item0
import kotlinx.coroutines.flow.flowOf

@Composable
fun AnimeByFiltersLVG(
    animeByFilters: LazyPagingItems<Item0>,
    onAnimeClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(animeByFilters.itemCount, key = { it }) { index ->
            val anime = animeByFilters[index]

            anime?.let {
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + anime.posters.small.url,
                    genresString = anime.genres.joinToString(", "),
                    title = anime.names.ru,
                    onCardClick = { onAnimeClick(anime.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Preview
@Composable
private fun AnimeByFiltersLVGPreview() {
    LibriaNowTheme {
        val emptyPagingItems = flowOf(PagingData.empty<Item0>()).collectAsLazyPagingItems()

        AnimeByFiltersLVG(
            animeByFilters = emptyPagingItems,
            onAnimeClick = {}
        )
    }
}