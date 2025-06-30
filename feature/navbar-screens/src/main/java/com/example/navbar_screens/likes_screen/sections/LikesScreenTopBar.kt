package com.example.navbar_screens.likes_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.navbar_screens.likes_screen.screen.LikesScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikesScreenTopBar(
    isLoading: Boolean,
    screenState: LikesScreenState,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit,
    onQueryInput: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                if(screenState.isSearching) {
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        value = screenState.query,
                        onValueChange = { onQueryInput(it) },
                        placeholder = {
                            Text(
                                text = "Поиск"
                            )
                        }
                    )
                } else {
                    Text(text = "Избранное")
                }
            },
            actions = {
                if (screenState.isSearching) {
                    if (screenState.query != "") {
                        IconButton(
                            onClick = onClearClick
                        ) {
                            Icon(
                                painter = painterResource(LibriaNowIcons.CloseCircle),
                                contentDescription = null
                            )
                        }
                    }
                } else {
                    IconButton(
                        onClick = onSearchClick
                    ) {
                        Icon(
                            painter = painterResource(LibriaNowIcons.Magnifier),
                            contentDescription = null
                        )
                    }
                }
            },
            navigationIcon = {
                if (screenState.isSearching) {
                    IconButton(
                        onClick = onSearchClick
                    ) {
                        Icon(
                            painter = painterResource(LibriaNowIcons.ArrowLeftFilled),
                            contentDescription = null
                        )
                    }
                }
            },
            scrollBehavior = scrollBehavior
        )

        val animationDuration = CommonConstants.ANIMATION_DURATION
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(tween(animationDuration)) + expandVertically(tween(animationDuration)),
            exit = fadeOut(tween(animationDuration)) + shrinkVertically(tween(animationDuration))
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun LikesScreenTopBarPreview() {
    LibriaNowTheme {
        val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        LikesScreenTopBar(
            isLoading = false,
            onSearchClick = {},
            scrollBehavior = topBarScrollBehavior,
            screenState = LikesScreenState(),
            onQueryInput = {},
            onClearClick = {},
        )
    }
}