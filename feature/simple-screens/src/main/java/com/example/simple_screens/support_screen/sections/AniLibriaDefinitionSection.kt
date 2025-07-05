package com.example.simple_screens.support_screen.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography

@Composable
fun AniLibriaDefinitionSection() {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = mColors.primary,
                    fontSize = mTypography.titleMedium.fontSize,
                    fontFamily = mTypography.titleMedium.fontFamily,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("AniLibria ")
            }

            withStyle(
                style = SpanStyle(
                    color = mColors.onBackground,
                    fontSize = mTypography.titleMedium.fontSize,
                    fontFamily = mTypography.titleMedium.fontFamily,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("- творческое объединение занимающееся озвучиванием аниме")
            }
        }
    )
}

@Preview
@Composable
fun AniLibriaDefinitionSectionPreview() {
    LibriaNowTheme {
        AniLibriaDefinitionSection()
    }
}