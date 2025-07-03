package com.example.player_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.mTypography

@Composable
fun BoxScope.Header(
    episode: Int,
    episodeTitle: String,
    topPadding: Dp,
    onBackClick: () -> Unit,
    onPlaylistClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .padding(
                top = topPadding,
                start = CommonConstants.HORIZONTAL_PADDING.dp,
                end = CommonConstants.HORIZONTAL_PADDING.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HeaderIconButton(iconRes = LibriaNowIcons.ArrowLeftFilled, onClick = onBackClick)

        Text(
            text = "${episode + 1} • $episodeTitle",
            style = mTypography.bodyLarge.copy(color = Color.White),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        HeaderIconButton(iconRes = LibriaNowIcons.Playlist, onClick = onPlaylistClick)
    }
}

@Composable
private fun HeaderIconButton(
    iconRes: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Box {
        Header(
            episode = 1,
            episodeTitle = "Название",
            topPadding = 12.dp,
            onBackClick = {},
            onPlaylistClick = {}
        )
    }
}