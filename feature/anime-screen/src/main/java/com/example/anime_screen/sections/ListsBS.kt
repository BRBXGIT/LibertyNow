package com.example.anime_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography
import com.example.local.db.lists_db.ListAnimeStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsBS(
    currentLists: List<ListAnimeStatus>,
    onDismissRequest: () -> Unit,
    onStatusSelected: (ListAnimeStatus) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            items(ListAnimeStatus.entries) { status ->
                val name = when(status) {
                    ListAnimeStatus.WATCHING -> "Смотрю"
                    ListAnimeStatus.COMPLETED -> "Просмотрено"
                    ListAnimeStatus.PLANNED -> "В планах"
                    ListAnimeStatus.ON_HOLD -> "Отложено"
                    ListAnimeStatus.DROPPED -> "Брошено"
                }

                ListItem(
                    name = name,
                    selected = status in currentLists,
                    onClick = { onStatusSelected(status) }
                )
            }
        }
    }
}

@Composable
fun ListItem(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(mShapes.small)
            .clickable {
                onClick()
            }
            .padding(12.dp)
    ) {
        Text(
            text = name,
            style = mTypography.bodyLarge
        )

        Icon(
            painter = painterResource(
                if (selected) LibriaNowIcons.CheckCircle else LibriaNowIcons.SingleArrowRightFilled
            ),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun ListItemPreview() {
    LibriaNowTheme {
        ListItem(
            name = "Просмотрено",
            selected = true,
            onClick = {}
        )
    }
}