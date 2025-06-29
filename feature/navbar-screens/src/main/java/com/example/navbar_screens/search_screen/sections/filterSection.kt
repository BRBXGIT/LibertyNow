package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mTypography

fun LazyGridScope.filterSection(
    releaseEnd: Boolean,
    onReleaseEndClick: () -> Unit
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Фильтр",
            style = mTypography.titleMedium
        )
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Checkbox(
                checked = releaseEnd,
                onCheckedChange = { onReleaseEndClick() }
            )

            Text(
                text = "Релиз завершен",
                style = mTypography.bodyLarge
            )
        }
    }
}