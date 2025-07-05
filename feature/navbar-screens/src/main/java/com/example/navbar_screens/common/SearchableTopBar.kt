package com.example.navbar_screens.common

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
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(
    title: String,
    query: String,
    isSearching: Boolean,
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    placeholder: String = "Поиск",
    onSearchClick: () -> Unit,
    onQueryInput: (String) -> Unit,
    onClearClick: () -> Unit,
    changeColor: Boolean = true
) {
    Column {
        TopAppBar(
            colors = if (changeColor) {
                TopAppBarDefaults.topAppBarColors()
            } else {
                TopAppBarDefaults.topAppBarColors(
                    scrolledContainerColor = mColors.background
                )
            },
            title = {
                if (isSearching) {
                    TextField(
                        value = query,
                        onValueChange = onQueryInput,
                        singleLine = true,
                        placeholder = { Text(placeholder) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                } else {
                    Text(title)
                }
            },
            actions = {
                when {
                    isSearching && query.isNotEmpty() -> {
                        IconButton(onClick = onClearClick) {
                            Icon(painterResource(LibriaNowIcons.CloseCircle), contentDescription = null)
                        }
                    }
                    !isSearching -> {
                        IconButton(onClick = onSearchClick) {
                            Icon(painterResource(LibriaNowIcons.Magnifier), contentDescription = null)
                        }
                    }
                }
            },
            navigationIcon = {
                if (isSearching) {
                    IconButton(onClick = onSearchClick) {
                        Icon(painterResource(LibriaNowIcons.ArrowLeftFilled), contentDescription = null)
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