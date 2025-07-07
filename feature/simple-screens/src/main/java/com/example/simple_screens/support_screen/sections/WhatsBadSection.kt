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
fun WhatsBadSection() {
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
                append("Что не круто\n")
            }

            withStyle(
                style = SpanStyle(
                    color = mColors.onBackground,
                    fontSize = mTypography.bodyLarge.fontSize,
                    fontFamily = mTypography.bodyLarge.fontFamily,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(
                    "-- Наш сайт заблокировал РосКомНадзор, поэтому у проекта сократились доходы"
                )
            }
        }
    )
}

@Preview
@Composable
private fun WhatsBadSectionPreview() {
    LibriaNowTheme {
        WhatsBadSection()
    }
}