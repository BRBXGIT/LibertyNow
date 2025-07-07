package com.example.simple_screens.info_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun InfoItemUi(
    onClick: () -> Unit,
    name: String,
    label: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(
            text = name,
            style = mTypography.bodyLarge
        )

        Text(
            text = label,
            style = mTypography.bodySmall.copy(
                color = mColors.primary
            )
        )
    }
}