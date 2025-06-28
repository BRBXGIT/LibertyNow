package com.example.navbar_screens.home_screen.sections

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
fun NothingHereSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Text(
            text = "Пока что ничего нет, начните вводить запрос :)",
            textAlign = TextAlign.Center,
            style = mTypography.titleMedium
        )
    }
}