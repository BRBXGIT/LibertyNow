package com.example.onboarding_screen.sections

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes

object StartButtonConstants {
    const val START_BUTTON_TEST_TAG = "StartButtonTestTag"

    const val START_BUTTON_TEXT = "Начать"
}

@Composable
fun StartButton(
    onClick: () -> Unit
) {
    Button(
        shape = mShapes.small,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(StartButtonConstants.START_BUTTON_TEST_TAG)
    ) {
        Text(
            text = StartButtonConstants.START_BUTTON_TEXT
        )
    }
}

@Preview
@Composable
fun StartButtonPreview() {
    LibriaNowTheme {
        StartButton(
            onClick = {}
        )
    }
}