package com.example.anime_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun GenresLR(
    genres: List<String>,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            GenreBox(genre)
        }
    }
}

@Composable
private fun GenreBox(
    genre: String,
) {
    Box(
        modifier = Modifier
            .clip(mShapes.extraSmall)
            .background(mColors.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre,
            style = mTypography.labelMedium,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Preview
@Composable
fun GenresLRPreview() {
    LibriaNowTheme {
        GenresLR(
            genres = listOf("Сёнен", "Пародия", "Комедия")
        )
    }
}

@Preview
@Composable
fun GenreBoxPreview() {
    LibriaNowTheme {
        GenreBox(genre = "Сёнен")
    }
}