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
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QualityBS(
    onClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val qualityItems = listOf(
        480,
        720,
        1080
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = mShapes.small
    ) {
        HorizontalDivider(modifier = Modifier.padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(qualityItems) { item ->
                QualityItem(
                    quality = item,
                    onClick = { onClick(item) }
                )
            }
        }
    }
}

@Composable
private fun QualityItem(
    quality: Int,
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
            text = quality.toString(),
            style = mTypography.bodyLarge
        )
    }
}