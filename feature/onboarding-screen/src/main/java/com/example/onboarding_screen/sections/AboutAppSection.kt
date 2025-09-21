package com.example.onboarding_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mTypography

object AboutAppSectionConstants {
    const val GREETING_TEXT = "Привет в LibertyNow"
    const val ABOUT_APP_TEXT = "LibertyNow это неофициальный андроид клиент AniLiberty, здесь вы можете смотреть аниме " +
            "в их озвучке. Если данные грузятся слишком долго, или у вас не " +
            "подгружаются картинки, попробуйте включить DPI спуфер или VPN"
}

@Composable
fun AboutAppSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = AboutAppSectionConstants.GREETING_TEXT,
            style = mTypography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = AboutAppSectionConstants.ABOUT_APP_TEXT,
            style = mTypography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun AboutAppSectionPreview() {
    LibriaNowTheme {
        AboutAppSection()
    }
}