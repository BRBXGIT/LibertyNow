package com.example.onboarding_screen.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors

object ScreenHeaderConstants {
    const val APP_NAME_TEXT = "LibertyNow\n"
    const val POWERED_BY_TEXT = "Powered by AniLiberty api"

    const val TEXT_TEST_TAG = "TextTestTag"
}

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
            append(ScreenHeaderConstants.APP_NAME_TEXT)
        }
        withStyle(
            style = SpanStyle(
                color = mColors.onBackground,
                fontSize = 16.sp,
            )
        ) {
            append(ScreenHeaderConstants.POWERED_BY_TEXT)
        }
    }

    Text(
        text = annotatedLibriaNowString,
        modifier = Modifier.testTag(ScreenHeaderConstants.TEXT_TEST_TAG)
    )
}

@Preview
@Composable
private fun ScreenHeaderPreview() {
    LibriaNowTheme {
        ScreenHeader()
    }
}