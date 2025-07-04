package com.example.simple_screens.settings_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoQualityBS(
    onDismissRequest: () -> Unit,
    onSetQualityClick: (Int) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small.copy(bottomEnd = CornerSize(0.dp), bottomStart = CornerSize(0.dp)),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        val qualityItems = listOf(
            480,
            720,
            1080
        )

        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(qualityItems) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(mShapes.extraSmall)
                        .clickable {
                            onSetQualityClick(item)
                            onDismissRequest()
                        }
                        .padding(8.dp)
                ) {
                    Text(text = item.toString())
                }
            }
        }
    }
}

@Preview
@Composable
fun VideoQualityBSPreview() {
    LibriaNowTheme {
        VideoQualityBS(
            onDismissRequest = {},
            onSetQualityClick = {},
        )
    }
}