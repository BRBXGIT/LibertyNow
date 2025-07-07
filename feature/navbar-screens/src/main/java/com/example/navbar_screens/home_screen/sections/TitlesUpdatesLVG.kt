package com.example.navbar_screens.home_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.cards.AnimeCard
import com.example.design_system.theme.DesignUtils
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes
import com.example.network.common.models.Item0
import kotlinx.coroutines.flow.flowOf

@Composable
fun TitlesUpdatesLVG(
    showRandomButton: Boolean = true,
    titlesUpdates: LazyPagingItems<Item0>,
    onAnimeClick: (Int) -> Unit,
    onRandomClick: () -> Unit = {}
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        if (showRandomButton) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Button(
                    onClick = onRandomClick,
                    shape = mShapes.extraLarge
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(LibriaNowIcons.Funny),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            text = "Случайное аниме"
                        )
                    }
                }
            }
        }

        items(titlesUpdates.itemCount, key = { it }) { index ->
            val anime = titlesUpdates[index]

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
private fun TitlesLitLVGPreview() {
    LibriaNowTheme {
        val emptyPagingItems = flowOf(PagingData.empty<Item0>()).collectAsLazyPagingItems()

        TitlesUpdatesLVG(
            showRandomButton = true,
            titlesUpdates = emptyPagingItems,
            onAnimeClick = {},
            onRandomClick = {}
        )
    }
}