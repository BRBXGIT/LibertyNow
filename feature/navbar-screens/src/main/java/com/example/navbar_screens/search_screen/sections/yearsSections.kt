package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import java.time.LocalDateTime

fun LazyGridScope.yearsSection(
    fromYear: Int,
    toYear: Int,
    onFromYearChange: (Int) -> Unit,
    onToYearChange: (Int) -> Unit
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            YearTextField(
                year = fromYear,
                label = "Начиная с года",
                onChange = { onFromYearChange(it) },
            )

            YearTextField(
                year = toYear,
                label = "Заканчивая годом",
                onChange = { onToYearChange(it) }
            )
        }
    }
}

@Composable
private fun YearTextField(
    year: Int,
    label: String,
    onChange: (Int) -> Unit
) {
    OutlinedTextField(
        value = year.toString(),
        onValueChange = {
            if (it != "") {
                onChange(it.filter { it.isDigit() }.toInt())
            }
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = label
            )
        }
    )
}

@Preview
@Composable
private fun YearsSectionPreview() {
    LibriaNowTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(90.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            yearsSection(
                fromYear = 0,
                toYear = LocalDateTime.now().year,
                onFromYearChange = {},
                onToYearChange = {},
            )
        }
    }
}