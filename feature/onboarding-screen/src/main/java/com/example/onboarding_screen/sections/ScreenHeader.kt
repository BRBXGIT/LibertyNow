package com.example.onboarding_screen.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors

@Composable
fun ScreenHeader() {
    val annotatedLibriaNowString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = mColors.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("LibriaNow\n")
        }
        withStyle(
            style = SpanStyle(
                color = mColors.onBackground,
                fontSize = 16.sp,
            )
        ) {
            append("Powered by AniLibria api")
        }
    }

    Text(
        text = annotatedLibriaNowString
    )
}

@Preview
@Composable
private fun ScreenHeaderPreview() {
    LibriaNowTheme {
        ScreenHeader()
    }
}