package com.example.anime_screen.sections

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons

@Composable
fun ContinueWatchFAB(
    start: Boolean,
    expanded: Boolean,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(LibriaNowIcons.PlayFilled),
                contentDescription = null,
                modifier = Modifier.size(12.dp)
            )
        },
        text = {
            Text(text = if (start) "Начать" else "Продолжить")
        },
        expanded = expanded,
    )
}