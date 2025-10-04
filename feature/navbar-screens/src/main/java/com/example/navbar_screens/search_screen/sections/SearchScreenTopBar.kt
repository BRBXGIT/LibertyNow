package com.example.navbar_screens.search_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme

object SearchScreenTopBarConstants {
    const val ACTION_BUTTON_TEST_TAG = "TopBarTestTag"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchScreenTopBar(
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onFiltersClick: () -> Unit,
    useExpressive: Boolean
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Расширенный поиск"
                )
            },
            actions = {
                IconButton(
                    onClick = onFiltersClick,
                    modifier = Modifier.testTag(SearchScreenTopBarConstants.ACTION_BUTTON_TEST_TAG)
                ) {
                    Icon(
                        painter = painterResource(LibriaNowIcons.Filters),
                        contentDescription = null
                    )
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
            if (useExpressive) {
                LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun SearchScreenTopBarPreview() {
    LibriaNowTheme {
        val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        SearchScreenTopBar(
            isLoading = false,
            onFiltersClick = {},
            scrollBehavior = topBarScrollBehavior,
            useExpressive = true
        )
    }
}