package com.example.player_screen.sections

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SkipOpeningButton(
    onClick: () -> Unit,
    secondsLeft: Int
) {
    OutlinedButton(
        onClick = onClick
    ) {
        Text(
            text = "Пропустить опенинг $secondsLeft"
        )
    }
}