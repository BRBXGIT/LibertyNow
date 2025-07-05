package com.example.navbar_screens.more_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreenTopBar(
    onLogOutClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.AniLibertyMilticolored),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Text(
                    text = "LibriaNow"
                )
            }
        },
        actions = {
            IconButton(
                onClick = onLogOutClick
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.Exit),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}