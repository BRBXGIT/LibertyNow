package com.example.player_screen.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme

@Composable
fun BoxScope.SkipOpeningButton(
    onClick: () -> Unit,
    secondsLeft: Int
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(
                bottom = 120.dp,
                end = CommonConstants.HORIZONTAL_PADDING.dp
            )
    ) {
        Text(
            text = "Пропустить опенинг $secondsLeft"
        )
    }
}

@Preview
@Composable
private fun SkipOpeningButtonPreview() {
    LibriaNowTheme {
        Box {
            SkipOpeningButton(
                onClick = {},
                secondsLeft = 10
            )
        }
    }
}