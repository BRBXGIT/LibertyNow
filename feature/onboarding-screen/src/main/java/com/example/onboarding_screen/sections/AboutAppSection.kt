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

@Composable
fun AboutAppSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Привет в LibertyNow",
            style = mTypography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = "LibertyNow это неофициальный андроид клиент AniLiberty, здесь вы можете смотреть аниме " +
                    "в их озвучке. Если данные грузятся слишком долго, или у вас не " +
                    "подгружаются картинки, попробуйте включить DPI спуфер или VPN",
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