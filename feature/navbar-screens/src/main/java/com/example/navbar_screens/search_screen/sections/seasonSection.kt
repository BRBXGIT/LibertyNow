package com.example.navbar_screens.search_screen.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.navbar_screens.search_screen.screen.Season

fun LazyGridScope.seasonSection(
    seasons: List<Season>,
    onSeasonClick: (Season) -> Unit,
    chosenSeasons: List<Season>
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Сезон",
            style = mTypography.titleMedium
        )
    }

    items(seasons) { season ->
        val surfaceAnimatedColor by animateColorAsState(
            targetValue = if(season in chosenSeasons) mColors.primary else mColors.surfaceContainerHigh,
            animationSpec = tween(200)
        )
        val onSurfaceAnimatedColor by animateColorAsState(
            targetValue = if(season in chosenSeasons) mColors.onPrimary else mColors.onSurface,
            animationSpec = tween(200)
        )

        val seasonName = when (season) {
            Season.Winter -> "Зима"
            Season.Spring -> "Весна"
            Season.Summer -> "Лето"
            Season.Autumn -> "Осень"
        }
        Surface(
            color = surfaceAnimatedColor,
            shape = mShapes.small,
            onClick = { onSeasonClick(season) }
        ) {
            Text(
                color = onSurfaceAnimatedColor,
                text = seasonName,
                modifier = Modifier.padding(4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun SeasonSectionPreview() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(90.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        seasonSection(
            seasons = listOf(Season.Winter, Season.Spring, Season.Summer, Season.Autumn),
            onSeasonClick = {},
            chosenSeasons = listOf(Season.Winter, Season.Autumn)
        )
    }
}