package com.example.navbar_screens.home_screen.sections

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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.design_system.theme.LibriaNowIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Последние обновления"
                )
            },
            actions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(LibriaNowIcons.Magnifier),
                        contentDescription = null
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(tween(300)) + expandVertically(tween(300)),
            exit = fadeOut(tween(300)) + shrinkVertically(tween(300))
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}