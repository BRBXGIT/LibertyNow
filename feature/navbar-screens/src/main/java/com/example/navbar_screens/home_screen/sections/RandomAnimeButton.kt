package com.example.navbar_screens.home_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.mShapes

@Composable
fun RandomAnimeButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = mShapes.extraLarge
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(LibriaNowIcons.Funny),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = "Случайное аниме"
            )
        }
    }
}