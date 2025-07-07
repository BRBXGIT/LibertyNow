package com.example.simple_screens.info_screen.sections

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme

@Composable
fun Header() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 12.dp)
    ) {
        Icon(
            painter = painterResource(LibriaNowIcons.AniLibertyMulticolored),
            contentDescription = null,
            modifier = Modifier.size(70.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() {
    LibriaNowTheme {
        Header()
    }
}