package com.example.player_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Immutable
data class SettingItem(
    val name: String,
    val valueLabel: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerSettingSheet(
    autoPlay: Boolean,
    quality: Int,
    showSkipOpeningButton: Boolean,
    onDismissRequest: () -> Unit,
    onQualityClick: () -> Unit,
    onAutoPlayClick: () -> Unit,
    onShowSkipOpeningButtonClick: () -> Unit
) {
    val settings = listOf(
        SettingItem(
            name = "Качество",
            valueLabel = quality.toString(),
            onClick = onQualityClick
        ),
        SettingItem(
            name = "Автовоспроизведение",
            valueLabel = if (autoPlay) "Да" else "Нет",
            onClick = onAutoPlayClick
        ),
        SettingItem(
            name = "Показывать кнопку пропуска опенинга",
            valueLabel = if (showSkipOpeningButton) "Да" else "Нет",
            onClick = onShowSkipOpeningButtonClick
        )
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = mShapes.small
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(settings) { item ->
                SettingRow(item)
            }
        }
    }
}

@Composable
private fun SettingRow(item: SettingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable(onClick = item.onClick)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = mTypography.bodyLarge
        )
        Text(
            text = item.valueLabel,
            style = mTypography.bodyLarge.copy(color = Color.Gray)
        )
    }
}

@Preview
@Composable
private fun SettingsItemPreview() {
    LibriaNowTheme {
        SettingRow(
            SettingItem(
                name = "Качество",
                valueLabel = "480",
                onClick = {}
            )
        )
    }
}