package com.example.anime_screen.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun DescriptionSection(
    description: String?,
    isExpanded: Boolean,
    voiceActors: String,
    timingWorkers: String,
    subtitlesWorkers: String,
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit
) {
    val parsedDescription = description?.let { AnnotatedString.fromHtml(it) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Column {
            Text("Озвучка: $voiceActors", style = mTypography.bodyLarge)
            Text("Тайминг: $timingWorkers", style = mTypography.bodyLarge)
            Text("Работа над субтитрами: $subtitlesWorkers", style = mTypography.bodyLarge)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.animateContentSize()
        ) {
            val animatedColor by animateColorAsState(
                targetValue = if (isExpanded) mColors.onBackground else mColors.background,
                label = "Animated color"
            )

            parsedDescription?.let {
                ExpandableText(
                    text = it,
                    isExpanded = isExpanded,
                    animatedColor = animatedColor
                )
            } ?: Text("Описания нет", style = mTypography.bodyLarge)

            if (!description.isNullOrBlank()) {
                val animatedImage = AnimatedImageVector.animatedVectorResource(LibriaNowIcons.ArrowDownAnimated)
                val animatedPainter = rememberAnimatedVectorPainter(
                    animatedImageVector = animatedImage,
                    atEnd = !isExpanded
                )

                Image(
                    painter = animatedPainter,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(mColors.onBackground),
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onExpandClick()
                    }
                )
            }
        }
    }
}

@Composable
private fun ExpandableText(
    text: AnnotatedString,
    isExpanded: Boolean,
    animatedColor: Color
) {
    Text(
        text = text,
        style = if (!isExpanded) {
            mTypography.bodyLarge.copy(
                brush = Brush.verticalGradient(
                    listOf(mColors.onBackground, animatedColor)
                )
            )
        } else {
            mTypography.bodyLarge
        },
        maxLines = if (!isExpanded) 5 else Int.MAX_VALUE,
        overflow = if (!isExpanded) TextOverflow.Ellipsis else TextOverflow.Clip
    )
}

@Preview
@Composable
private fun DescriptionSectionPreview() {
    LibriaNowTheme {
        DescriptionSection(
            isExpanded = false,
            description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                    " Aenean commodore ligula egret dolor. Aeneas massa. Cum sociis natoque penatibus" +
                    " et magnetism dis prurient montes, nascent ridiculous mus. Done quam felis, ultricies" +
                    " nec, pellentesque eu, premium quits, sem. Null consequent massa quiz denim. Donec pede justo," +
                    " fringilla vel, aliquot nec, amputate get, arc. In enum justo, rhonchus ut, imper diet a,",
            onExpandClick = {},
            voiceActors = "Ancord, Cuba77, BRBX",
            timingWorkers = "BRBX, BRBX, BRBX",
            subtitlesWorkers = "Egork, Vova228, XCRT",
        )
    }
}