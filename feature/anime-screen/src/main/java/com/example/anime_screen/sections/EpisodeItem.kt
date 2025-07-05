package com.example.anime_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun EpisodeItem(
    modifier: Modifier = Modifier,
    episode: Int,
    name: String,
    isWatched: Boolean,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
            .clip(mShapes.small)
            .clickable {
                onClick()
            }
            .background(
                if (isWatched) mColors.surfaceContainerLow else mColors.surfaceContainerHigh
            )
    ) {
        Text(
            text = "$episode • $name",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = mTypography.bodyLarge,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 20.dp
            )
        )
    }
}

@Preview
@Composable
private fun EpisodeItemPreview() {
    LibriaNowTheme {
        EpisodeItem(
            episode = 1,
            name = "Новый эпизод",
            onClick = {},
            isWatched = true
        )
    }
}