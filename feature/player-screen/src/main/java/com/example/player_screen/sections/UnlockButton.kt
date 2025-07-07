package com.example.player_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme

@Composable
fun BoxScope.UnlockButton(
    onClick: () -> Unit,
    bottomPadding: Dp
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = bottomPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.Unlock),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "Разблокировать"
            )
        }
    }
}

@Preview
@Composable
private fun UnlockButtonPreview() {
    LibriaNowTheme {
        Box {
            UnlockButton(
                onClick = {},
                bottomPadding = 16.dp
            )
        }
    }
}