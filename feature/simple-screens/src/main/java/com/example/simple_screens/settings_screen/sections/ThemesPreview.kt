package com.example.simple_screens.settings_screen.sections

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun ThemePreviewsSection(
    chosenTheme: String,
    onThemeClick: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(mShapes.small)
            .border(
                width = 1.dp,
                color = mColors.secondary,
                shape = mShapes.small
            )
    ) {
        ThemeElement(
            type = "default",
            text = "Система",
            onClick = { onThemeClick("default") },
            chosenTheme = chosenTheme
        )

        ThemeElement(
            type = "light",
            text = "Светлая",
            onClick = { onThemeClick("light") },
            chosenTheme = chosenTheme
        )

        ThemeElement(
            type = "dark",
            text = "Тёмная",
            onClick = { onThemeClick("dark") },
            chosenTheme = chosenTheme
        )

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ThemeElement(
                type = "dynamic",
                text = "Динамическая",
                onClick = { onThemeClick("dynamic") },
                chosenTheme = chosenTheme
            )
        }
    }
}

@Composable
fun RowScope.ThemeElement(
    //Light, dark, etc.
    type: String,
    text: String,
    onClick: () -> Unit,
    chosenTheme: String
) {
    val chosen = chosenTheme == type
    val chosenColor by animateColorAsState(
        targetValue = if(chosen) mColors.primaryContainer else Color.Transparent,
        label = "Animated color"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(chosenColor)
            .clickable {
                onClick()
            }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = mTypography.labelLarge.copy(
                color = if(chosen) mColors.onPrimaryContainer else mColors.onBackground,
                fontWeight = if(chosen) FontWeight.Bold else FontWeight.Normal
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}