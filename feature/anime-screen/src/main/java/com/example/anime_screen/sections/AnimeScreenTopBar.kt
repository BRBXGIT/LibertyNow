package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreenTopBar(
    isError: Boolean,
    animeTitle: String?,
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = isLoading,
                enter = slideInVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = slideOutVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    targetOffsetY = { -it / 2 }
                ) + fadeOut(tween(CommonConstants.ANIMATION_DURATION))
            ) {
                Text(
                    text = "Loading..."
                )
            }

            AnimatedVisibility(
                visible = ((!isLoading) and (animeTitle != null) and (!isError)),
                enter = slideInVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = slideOutVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    targetOffsetY = { -it / 2 }
                ) + fadeOut(tween(CommonConstants.ANIMATION_DURATION))
            ) {
                Text(
                    text = animeTitle!!
                )
            }

            AnimatedVisibility(
                visible = isError,
                enter = slideInVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    initialOffsetY = { it / 2 }
                ) + fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
                exit = slideOutVertically(
                    animationSpec = tween(CommonConstants.ANIMATION_DURATION),
                    targetOffsetY = { -it / 2 }
                ) + fadeOut(tween(CommonConstants.ANIMATION_DURATION))
            ) {
                Text(
                    text = "ERROR"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AnimeScreenTopBaPreview() {
    LibriaNowTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        AnimeScreenTopBar(
            animeTitle = "Death Note",
            isLoading = false,
            scrollBehavior = scrollBehavior,
            isError = false
        )
    }
}