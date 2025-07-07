package com.example.navbar_screens.lists_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.cards.AnimeCard
import com.example.design_system.theme.DesignUtils
import com.example.design_system.theme.LibriaNowTheme
import com.example.local.db.lists_db.ListsAnimeEntity

@Composable
fun ListLVG(
    list: List<ListsAnimeEntity>,
    onAnimeClick: (Int) -> Unit
) {
    if (list.isEmpty()) {
        EmptyPageSection()
    } else {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list, key = { it.id }) { anime ->
                AnimeCard(
                    posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster,
                    genresString = anime.genres,
                    title = anime.name,
                    onCardClick = { onAnimeClick(anime.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Preview
@Composable
private fun ListsLVGPreview() {
    LibriaNowTheme {
        ListLVG(
            list = emptyList(),
            onAnimeClick = {}
        )
    }
}