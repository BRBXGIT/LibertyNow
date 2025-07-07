package com.example.simple_screens.project_team_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectTeamScreenTopBar(
    loadingState: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit
) {
    Column {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(text = "Команда проекта")
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(id = LibriaNowIcons.ArrowLeftFilled),
                        contentDescription = null
                    )
                }
            }
        )

        AnimatedVisibility(
            visible = loadingState,
            enter = fadeIn(tween(CommonConstants.ANIMATION_DURATION)) + expandVertically(tween(CommonConstants.ANIMATION_DURATION)),
            exit = fadeOut(tween(CommonConstants.ANIMATION_DURATION)) + shrinkVertically(tween(CommonConstants.ANIMATION_DURATION))
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProjectTeamScreenTopBarPreview() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LibriaNowTheme {
        ProjectTeamScreenTopBar(
            loadingState = false,
            scrollBehavior = scrollBehavior,
            onBackClick = {}
        )
    }
}