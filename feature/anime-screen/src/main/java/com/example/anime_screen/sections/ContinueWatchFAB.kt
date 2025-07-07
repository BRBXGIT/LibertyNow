package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
private fun ContinueWatchFAB(
    start: Boolean,
    expanded: Boolean,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(LibriaNowIcons.PlayFilled),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        },
        text = {
            Text(text = if (start) "Начать" else "Продолжить")
        },
        expanded = expanded,
    )
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