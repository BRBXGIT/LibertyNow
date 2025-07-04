package com.example.simple_screens.settings_screen.sections

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
fun SettingsScreenTopBar(
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Настройки"
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.ArrowLeftFilled),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SettingsScreenTopBarPreview() {
    LibriaNowTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        SettingsScreenTopBar(
            onBackClick = {},
            scrollBehavior = scrollBehavior
        )
    }
}