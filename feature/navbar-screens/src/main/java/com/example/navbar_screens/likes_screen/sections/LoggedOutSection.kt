package com.example.navbar_screens.likes_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mTypography

@Composable
fun LoggedOutSection(
    onAuthClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Что-бы просматривать избранное и добавлять туда тайтлы нужно авторизоваться :)",
                style = mTypography.bodyLarge,
                textAlign = TextAlign.Center
            )

            OutlinedButton(
                onClick = onAuthClick
            ) {
                Text(text = "Авторизоваться")
            }
        }
    }
}

@Preview
@Composable
private fun LoggedOutSectionPreview() {
    LibriaNowTheme {
        LoggedOutSection(
            onAuthClick = {}
        )
    }
}