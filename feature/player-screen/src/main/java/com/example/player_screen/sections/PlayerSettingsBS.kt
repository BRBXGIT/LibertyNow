package com.example.player_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

data class SettingItem(
    val name: String,
    val label: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSettingsBS(
    autoPlay: Boolean,
    quality: Int,
    showSkipOpeningButton: Boolean,
    onDismissRequest: () -> Unit,
    onQualityClick: () -> Unit,
    onAutoPlayClick: () -> Unit,
    onShowSkipOpeningButtonClick: () -> Unit
) {
    val settingsItems = listOf(
        SettingItem(
            name = "Качество",
            label = quality.toString(),
            onClick = onQualityClick
        ),
        SettingItem(
            name = "Автовоспроизведение",
            label = if (autoPlay) "Да" else "Нет",
            onClick = onAutoPlayClick
        ),
        SettingItem(
            name = "Показывать кнопку пропуска опенинга",
            label = if (showSkipOpeningButton) "Да" else "Нет",
            onClick = onShowSkipOpeningButtonClick
        ),
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = mShapes.small
    ) {
        HorizontalDivider(modifier = Modifier.padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(settingsItems) { item ->
                SettingsItem(
                    name = item.name,
                    label = item.label,
                    onClick = item.onClick
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(
    name: String,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable {
                onClick()
            }
            .padding(12.dp)
    ) {
        Text(
            text = "$name ($label)",
            style = mTypography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun SettingsItemPreview() {
    LibriaNowTheme {
        SettingsItem(
            name = "Качество",
            label = "480",
            onClick = {}
        )
    }
}