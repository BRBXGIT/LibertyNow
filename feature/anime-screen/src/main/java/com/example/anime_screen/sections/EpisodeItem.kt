package com.example.anime_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onWatchButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
            .background(
                color = if (isWatched) mColors.surfaceContainerLow else mColors.surfaceContainerHigh,
                shape = mShapes.small
            )
            .padding(8.dp),
    ) {
        Text(
            text = "$episode • $name",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = mTypography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(end = CommonConstants.HORIZONTAL_PADDING.dp)
        )

        TextButton(
            onClick = onWatchButtonClick,
        ) {
            Text(
                text = "Смотреть",
            )
        }
    }
}

@Preview
@Composable
private fun EpisodeItemPreview() {
    LibriaNowTheme {
        EpisodeItem(
            episode = 1,
            name = "Новый эпизод",
            onWatchButtonClick = {},
            isWatched = true
        )
    }
}