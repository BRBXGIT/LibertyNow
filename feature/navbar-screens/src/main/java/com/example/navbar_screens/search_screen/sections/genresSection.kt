package com.example.navbar_screens.search_screen.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

fun LazyGridScope.genresSection(
    genres: List<String>,
    onGenreClick: (String) -> Unit,
    chosenGenres: List<String>,
    isLoading: Boolean
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Жанр",
            style = mTypography.titleMedium
        )
    }

    if(isLoading) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    } else {
        items(genres) { genre ->
            val surfaceAnimatedColor by animateColorAsState(
                targetValue = if(genre in chosenGenres) mColors.primary else mColors.surfaceContainerHigh,
                animationSpec = tween(200)
            )
            val onSurfaceAnimatedColor by animateColorAsState(
                targetValue = if(genre in chosenGenres) mColors.onPrimary else mColors.onSurface,
                animationSpec = tween(200)
            )

            Surface(
                color = surfaceAnimatedColor,
                shape = mShapes.small,
                onClick = { onGenreClick(genre) }
            ) {
                Text(
                    color = onSurfaceAnimatedColor,
                    text = genre,
                    modifier = Modifier.padding(4.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}