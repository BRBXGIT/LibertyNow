package com.example.player_screen.sections

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography
import kotlin.math.absoluteValue

@Composable
fun BoxScope.Footer(
    duration: Long,
    currentPosition: Long,
    bottomPadding: Dp,
    isSeeking: Boolean,
    isCropped: Boolean,
    onValueChange: (Long) -> Unit,
    onValueChangeFinished: (Long) -> Unit,
    onLockClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCropClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .padding(
                start = CommonConstants.HORIZONTAL_PADDING.dp,
                end = CommonConstants.HORIZONTAL_PADDING.dp,
                bottom = bottomPadding
            )
    ) {
        var sliderPosition by rememberSaveable { mutableFloatStateOf(0f) }
        LaunchedEffect(currentPosition, duration) {
            if ((duration > 0) and (!isSeeking)) {
                sliderPosition = currentPosition.toFloat() / duration
            }
        }

        Slider(
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = mColors.secondary,
                activeTrackColor = mColors.secondary,
                inactiveTrackColor = mColors.secondaryContainer,
            ),
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                val newPosition = (sliderPosition * duration).toLong()
                onValueChange(newPosition)
            },
            onValueChangeFinished = {
                val newPosition = (sliderPosition * duration).toLong()
                onValueChangeFinished(newPosition)
            }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val formattedCurrentPosition = formatMS(currentPosition)
            val formattedDuration = formatMS(duration)
            Text(
                text = "$formattedCurrentPosition / $formattedDuration",
                style = mTypography.bodyLarge.copy(
                    color = Color(0xFFFFFFFF)
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onLockClick
                ) {
                    Icon(
                        painter = painterResource(LibriaNowIcons.Lock),
                        contentDescription = null,
                        tint = Color(0xFFFFFFFF)
                    )
                }

                IconButton(
                    onClick = onSettingsClick
                ) {
                    Icon(
                        painter = painterResource(LibriaNowIcons.Settings),
                        contentDescription = null,
                        tint = Color(0xFFFFFFFF)
                    )
                }

                IconButton(
                    onClick = onCropClick
                ) {
                    val animatedImage = AnimatedImageVector.animatedVectorResource(LibriaNowIcons.CropAnimated)
                    val animatedPainter = rememberAnimatedVectorPainter(animatedImageVector = animatedImage, atEnd = !isCropped)

                    Image(
                        painter = animatedPainter,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color(0xFFFFFFFF))
                    )
                }
            }
        }
    }
}

private fun formatMS(ms: Long): String {
    val totalSeconds = ms.absoluteValue / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

@Preview
@Composable
fun FooterPreview() {
    LibriaNowTheme {
        Box {
            Footer(
                duration = 1000L,
                currentPosition = 500L,
                bottomPadding = 16.dp,
                onValueChangeFinished = {},
                onValueChange = {},
                isSeeking = false,
                isCropped = false,
                onLockClick = {},
                onSettingsClick = {},
                onCropClick = {},
            )
        }
    }
}