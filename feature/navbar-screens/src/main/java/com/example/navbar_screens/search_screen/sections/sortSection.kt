package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mTypography
import com.example.navbar_screens.search_screen.screen.SortedBy

fun LazyGridScope.sortSection(
    sort: SortedBy,
    onSortClick: (SortedBy) -> Unit
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = "Сортировка",
            style = mTypography.titleMedium
        )
    }

    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RadioButton(
                    selected = sort == SortedBy.Popularity,
                    onClick = { onSortClick(SortedBy.Popularity) }
                )

                Text(
                    text = "По популярности",
                    style = mTypography.bodyLarge
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )  {
                RadioButton(
                    selected = sort == SortedBy.Novelty,
                    onClick = { onSortClick(SortedBy.Novelty) }
                )

                Text(
                    text = "По новизне",
                    style = mTypography.bodyLarge
                )
            }
        }
    }
}