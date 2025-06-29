package com.example.navbar_screens.search_screen.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

fun LazyGridScope.yearsSection(
    years: List<Int>,
    onYearClick: (Int) -> Unit,
    chosenYears: List<Int>,
    isLoading: Boolean,
    isError: Boolean,
    onRetryClick: () -> Unit
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Год",
            style = mTypography.titleMedium
        )
    }

    if (isError) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Button(
                onClick = onRetryClick,
                shape = mShapes.small
            ) {
                Text(
                    text = "Retry"
                )
            }
        }
    } else {
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
            items(years) { year ->
                val surfaceAnimatedColor by animateColorAsState(
                    targetValue = if(year in chosenYears) mColors.primary else mColors.surfaceContainerHigh,
                    animationSpec = tween(200)
                )
                val onSurfaceAnimatedColor by animateColorAsState(
                    targetValue = if(year in chosenYears) mColors.onPrimary else mColors.onSurface,
                    animationSpec = tween(200)
                )

                Surface(
                    color = surfaceAnimatedColor,
                    shape = mShapes.small,
                    onClick = { onYearClick(year) }
                ) {
                    Text(
                        color = onSurfaceAnimatedColor,
                        text = year.toString(),
                        modifier = Modifier.padding(4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun YearsSectionPreview() {
    LibriaNowTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(90.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            yearsSection(
                years = listOf(2005, 2006, 2007),
                onYearClick = {},
                chosenYears = listOf(2005, 2006),
                isLoading = false,
                isError = false,
                onRetryClick = {}
            )
        }
    }
}