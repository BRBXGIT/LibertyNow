package com.example.player_screen.sections

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.player_screen.screen.IsPlayingState

@Composable
fun BoxScope.CentralButtonsSection(
    firstEpisode: Boolean,
    lastEpisode: Boolean,
    isPlaying: IsPlayingState,
    onPreviousClick: () -> Unit,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .align(Alignment.Center)
    ) {
        val animatedPreviousIconAlpha by animateFloatAsState(
            targetValue = if (firstEpisode) 0.3f else 1f
        )
        IconButton(
            onClick = onPreviousClick
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.PreviousFilled),
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(28.dp)
                    .alpha(animatedPreviousIconAlpha),
            )
        }

        if (isPlaying is IsPlayingState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = Color(0xffffffff)
            )
        } else {
            IconButton(
                onClick = onPlayClick
            ) {
                val animatedImage = AnimatedImageVector.animatedVectorResource(LibriaNowIcons.PlayAnimated)
                val animatedPainter = rememberAnimatedVectorPainter(
                    animatedImageVector = animatedImage,
                    atEnd = isPlaying is IsPlayingState.Playing
                )

                Image(
                    painter = animatedPainter,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFFFFFFFF)),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        val animatedNextIconAlpha by animateFloatAsState(
            targetValue = if (lastEpisode) 0.3f else 1f
        )
        IconButton(
            onClick = onNextClick
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.NextFilled),
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(28.dp)
                    .alpha(animatedNextIconAlpha),
            )
        }
    }
}

@Preview
@Composable
fun CentralButtonsSectionPreview() {
    LibriaNowTheme {
        Box {
            CentralButtonsSection(
                firstEpisode = false,
                lastEpisode = false,
                isPlaying = IsPlayingState.Playing,
                onPreviousClick = {},
                onPlayClick = {},
                onNextClick = {}
            )
        }
    }
}