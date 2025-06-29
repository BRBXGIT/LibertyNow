package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.mShapes
import com.example.navbar_screens.search_screen.screen.SearchScreenState
import com.example.navbar_screens.search_screen.screen.Season
import com.example.navbar_screens.search_screen.screen.SortedBy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBS(
    screenState: SearchScreenState,
    onDismissRequest: () -> Unit,
    onReleaseEndClick: () -> Unit,
    onSortClick: (SortedBy) -> Unit,
    onYearClick: (Int) -> Unit,
    onSeasonClick: (Season) -> Unit,
    onGenreClick: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(90.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            filterSection(
                releaseEnd = screenState.releaseEnd,
                onReleaseEndClick = onReleaseEndClick
            )

            sortSection(
                sort = screenState.sortedBy,
                onSortClick = { onSortClick(it) }
            )

            yearsSection(
                years = screenState.animeYears,
                onYearClick = { onYearClick(it) },
                chosenYears = screenState.chosenAnimeYears,
                isLoading = screenState.isAnimeYearsLoading
            )

            seasonSection(
                seasons = screenState.animeSeasons,
                onSeasonClick = { onSeasonClick(it) },
                chosenSeasons = screenState.chosenSeasons
            )

            genresSection(
                genres = screenState.animeGenres,
                onGenreClick = { onGenreClick(it) },
                chosenGenres = screenState.chosenAnimeGenres,
                isLoading = screenState.isAnimeGenresLoading
            )
        }
    }
}