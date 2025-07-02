package com.example.anime_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.design_system.cards.LibriaNowAsyncImage
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource

@Composable
fun Header(
    nameEnglish: String,
    season: String,
    type: String?,
    episodes: Int,
    releaseState: String,
    posterPath: String,
    topInnerPadding: Dp,
    modifier: Modifier = Modifier
) {
    val hazeState = remember { HazeState() }
    val posterHeight = 140.dp
    val typeText = type?.let { "$it $episodes эп." } ?: "Тип не указан"

    val hazeStyle = HazeStyle(
        backgroundColor = mColors.background,
        blurRadius = 8.dp,
        tint = HazeTint(
            color = mColors.background.copy(alpha = 0.5f),
            blendMode = BlendMode.SrcOver
        )
    )

    Box(modifier = modifier) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(posterPath)
                .crossfade(500)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .hazeSource(hazeState),
            filterQuality = FilterQuality.Low,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .hazeEffect(state = hazeState, style = hazeStyle),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, mColors.background)
                        )
                    )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = topInnerPadding,
                    start = CommonConstants.HORIZONTAL_PADDING.dp,
                    end = CommonConstants.HORIZONTAL_PADDING.dp,
                    bottom = 16.dp
                )
        ) {
            PosterImage(posterPath, posterHeight)

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(posterHeight)
            ) {
                Text(
                    text = nameEnglish,
                    style = mTypography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(season, style = mTypography.bodyLarge)
                    Text(typeText, style = mTypography.bodyLarge)
                    Text(
                        releaseState,
                        style = mTypography.bodyLarge.copy(color = mColors.primary)
                    )
                }
            }
        }
    }
}

@Composable
private fun PosterImage(path: String, height: Dp) {
    Box(
        modifier = Modifier
            .size(100.dp, height)
            .clip(mShapes.small)
    ) {
        LibriaNowAsyncImage(path)
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    LibriaNowTheme {
        Header(
            nameEnglish = "Тетрадь смерти",
            season = "2007 осень",
            type = "TV",
            releaseState = "Закончен",
            posterPath = "",
            episodes = 12,
            topInnerPadding = 16.dp
        )
    }
}