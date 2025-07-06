package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreenTopBar(
    animeInList: Boolean,
    isError: Boolean,
    animeTitle: String?,
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onArchiveClick: () -> Unit,
    onNavClick: () -> Unit
) {
    val showLoading = isLoading
    val showTitle = !isLoading && animeTitle != null && !isError
    val showError = isError

    TopAppBar(
        title = {
            AnimatedTopBarContent(visible = showLoading) {
                AnimatedLoadingText()
            }

            AnimatedTopBarContent(visible = showTitle) {
                Text(
                    text = animeTitle!!,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            AnimatedTopBarContent(visible = showError) {
                Text("Error")
            }
        },
        actions = {
            IconButton(onClick = onArchiveClick) {
                Icon(
                    painter = painterResource(
                        if (animeInList) LibriaNowIcons.ListCheckedFilled else LibriaNowIcons.Archive
                    ),
                    contentDescription = null
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(
                    painter = painterResource(LibriaNowIcons.ArrowLeftFilled),
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mColors.surfaceContainer.copy(alpha = 0f),
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun AnimatedTopBarContent(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(CommonConstants.ANIMATION_DURATION),
            initialOffsetY = { it / 2 }
        ) + fadeIn(tween(CommonConstants.ANIMATION_DURATION)),
        exit = slideOutVertically(
            animationSpec = tween(CommonConstants.ANIMATION_DURATION),
            targetOffsetY = { -it / 2 }
        ) + fadeOut(tween(CommonConstants.ANIMATION_DURATION))
    ) {
        content()
    }
}

@Composable
private fun AnimatedLoadingText() {
    var dotCount by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(200)
            dotCount = (dotCount + 1) % 4
        }
    }

    val animatedDots = List(3) { index ->
        animateFloatAsState(
            targetValue = if (index < dotCount) 1f else 0f,
            animationSpec = tween(durationMillis = 150)
        )
    }

    Row {
        Text(text = "Loading")
        animatedDots.forEach { alpha ->
            Text(
                text = ".",
                modifier = Modifier.alpha(alpha.value),
            )
        }
    }
}

@Preview
@Composable
private fun AnimatedLoadingTextPreview() {
    LibriaNowTheme {
        AnimatedLoadingText()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun AnimeScreenTopBaPreview() {
    LibriaNowTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        AnimeScreenTopBar(
            animeTitle = "Death Note",
            isLoading = false,
            scrollBehavior = scrollBehavior,
            isError = false,
            onArchiveClick = {},
            onNavClick = {},
            animeInList = true
        )
    }
}