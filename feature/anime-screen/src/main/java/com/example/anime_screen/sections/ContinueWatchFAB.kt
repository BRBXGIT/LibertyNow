package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anime_screen.screen.AnimeScreenState
import com.example.anime_screen.screen.ScrollDirection
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.google.gson.Gson

private val EasingLinearCubicBezier = CubicBezierEasing(0.0f, 0.0f, 1.0f, 1.0f)
private val EasingEmphasizedCubicBezier = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)

private val ExtendedFabMinimumWidth = 80.dp
private val ExtendedFabIconSize = 24.0.dp
private val ExtendedFabIconPadding = 12.dp
private val ExtendedFabTextPadding = 16.dp

private val ExtendedFabCollapseAnimation = fadeOut(
    animationSpec = tween(
        durationMillis = 100,
        easing = EasingLinearCubicBezier,
    ),
) + shrinkHorizontally(
    animationSpec = tween(
        durationMillis = 500,
        easing = EasingEmphasizedCubicBezier,
    ),
    shrinkTowards = Alignment.Start,
)

private val ExtendedFabExpandAnimation = fadeIn(
    animationSpec = tween(
        durationMillis = 200,
        delayMillis = 100,
        easing = EasingLinearCubicBezier,
    ),
) + expandHorizontally(
    animationSpec = tween(
        durationMillis = 500,
        easing = EasingEmphasizedCubicBezier,
    ),
    expandFrom = Alignment.Start,
)

private val FabContainerWidth = 56.0.dp

@Composable
private fun ContinueWatchFAB(
    start: Boolean,
    expanded: Boolean,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        val minWidth by animateDpAsState(
            targetValue = if (expanded) ExtendedFabMinimumWidth else FabContainerWidth,
            animationSpec = tween(
                durationMillis = 500,
                easing = EasingEmphasizedCubicBezier,
            ),
            label = "minWidth",
        )
        val startPadding by animateDpAsState(
            targetValue = if (expanded) ExtendedFabIconSize / 2 else 0.dp,
            animationSpec = tween(
                durationMillis = if (expanded) 300 else 900,
                easing = EasingEmphasizedCubicBezier,
            ),
            label = "startPadding",
        )

        Row(
            modifier = Modifier
                .sizeIn(minWidth = minWidth)
                .padding(start = startPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.PlayFilled),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
            AnimatedVisibility(
                visible = expanded,
                enter = ExtendedFabExpandAnimation,
                exit = ExtendedFabCollapseAnimation,
            ) {
                Box(modifier = Modifier.padding(start = ExtendedFabIconPadding, end = ExtendedFabTextPadding)) {
                    Text(text = if (start) "Начать" else "Продолжить")
                }
            }
        }
    }
}

@Composable
fun ContinueWatchFABWrapper(
    screenState: AnimeScreenState,
    onClick: (Int, String, String, Int) -> Unit,
    animeId: Int
) {
    if (screenState.anime?.player?.list != null) {
        AnimatedVisibility(
            visible = (!screenState.isLoading) and (screenState.anime.player.list.isNotEmpty()),
            enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
            exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION))
        ) {
            ContinueWatchFAB(
                start = screenState.watchedEps.isEmpty(),
                expanded = screenState.scrollDirection == ScrollDirection.Up,
                onClick = {
                    val anime = screenState.anime
                    val currentEpisodeId = (screenState.watchedEps.maxOrNull()?.plus(1) ?: 0)
                        .coerceAtMost(anime.player.list.size - 1)
                    val links = anime.player.list.values.toList()
                    val linksString = Gson().toJson(links)

                    onClick(currentEpisodeId, linksString, anime.player.host, animeId)
                }
            )
        }
    }
}

@Preview
@Composable
fun ContinueWatchFABPreview() {
    LibriaNowTheme {
        ContinueWatchFAB(
            start = false,
            expanded = true,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun ContinueWatchFABWrapperPreview() {
    LibriaNowTheme {
        ContinueWatchFABWrapper(
            screenState = AnimeScreenState(),
            animeId = 9934,
            onClick = { currentEpisodeId, linksString, host, animeId ->

            }
        )
    }
}