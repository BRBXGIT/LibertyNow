package com.example.player_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
// noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Slider
// noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
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
    onValueChange: (Long) -> Unit,
    onValueChangeFinished: (Long) -> Unit
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

        val formattedCurrentPosition = formatMS(currentPosition)
        val formattedDuration = formatMS(duration)
        Text(
            text = "$formattedCurrentPosition / $formattedDuration",
            style = mTypography.bodyLarge
        )
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
                isSeeking = false
            )
        }
    }
}