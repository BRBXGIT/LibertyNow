package com.example.player_screen.sections

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.player_screen.screen.IsPlayingState

@Composable
fun BoxScope.CentralButtonsSection(
    isPlaying: IsPlayingState,
    onPreviousClick: () -> Unit,
    onPlayClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.align(Alignment.Center),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        IconButton(
            onClick = onPreviousClick
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.PreviousFilled),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFFFFFFF)
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

        IconButton(
            onClick = onNextClick
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.NextFilled),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = Color(0xFFFFFFFF)
            )
        }
    }
}