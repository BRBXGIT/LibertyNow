package com.example.simple_screens.support_screen.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun DonateItemUi(
    onClick: () -> Unit,
    icon: Int,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable {
                onClick()
            }
            .border(
                width = 1.dp,
                shape = mShapes.small,
                color = mColors.tertiaryContainer
            )
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = text,
            style = mTypography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun DonateItemUiPreview() {
    LibriaNowTheme {
        DonateItemUi(
            onClick = {},
            icon = LibriaNowIcons.YoomoneyMulticolored,
            text = "Yoomoney"
        )
    }
}