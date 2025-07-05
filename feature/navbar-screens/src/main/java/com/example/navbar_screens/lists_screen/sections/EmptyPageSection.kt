package com.example.navbar_screens.lists_screen.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.mTypography

@Composable
fun EmptyPageSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp),
    ) {
        Text(
            text = "Кажется здесь ничего нет, добавьте что-нибудь :)",
            style = mTypography.bodyLarge.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}