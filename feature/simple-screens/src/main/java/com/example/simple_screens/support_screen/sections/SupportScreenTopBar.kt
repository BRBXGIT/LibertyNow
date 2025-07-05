package com.example.simple_screens.support_screen.sections

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreenTopBar(
    onNavIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Поддержать"
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.ArrowLeftFilled),
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SupportScreenTopBarPreview() {
    LibriaNowTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        SupportScreenTopBar(
            onNavIconClick = {},
            scrollBehavior = scrollBehavior
        )
    }
}