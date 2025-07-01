package com.example.player_screen.sections

import androidx.compose.foundation.layout.Arrangement
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .padding(
                top = topPadding,
                start = CommonConstants.HORIZONTAL_PADDING.dp,
                end = CommonConstants.HORIZONTAL_PADDING.dp
            )
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.ArrowLeftFilled),
                contentDescription = null,
                tint = Color(0xFFFFFFFF)
            )
        }

        Text(
            text = "${episode + 1} • $episodeTitle",
            style = mTypography.bodyLarge.copy(
                color = Color(0xFFFFFFFF)
            )
        )

        IconButton(
            onClick = onPlaylistClick,
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.Playlist),
                contentDescription = null,
                tint = Color(0xFFFFFFFF)
            )
        }
    }
}