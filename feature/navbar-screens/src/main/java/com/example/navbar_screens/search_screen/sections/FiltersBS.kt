package com.example.navbar_screens.search_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.mShapes
import com.example.navbar_screens.search_screen.screen.SearchScreenIntent
import com.example.navbar_screens.search_screen.screen.SearchScreenState
import com.example.navbar_screens.search_screen.screen.SearchScreenVM

object FiltersBSConstants {
    const val BOTTOM_SHEET_TEST_TAG = "BottomSheetTestTag"
    const val FILTERS_LVG_TEST_TAG = "FiltersLVGTestTag"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersBS(
    screenState: SearchScreenState,
    topInnerPadding: Dp,
    viewModel: SearchScreenVM,
) {
    ModalBottomSheet(
        onDismissRequest = { viewModel.sendIntent(SearchScreenIntent.ChangeFiltersBSVisible) },
        shape = mShapes.small,
        modifier = Modifier
            .padding(top = topInnerPadding)
            .testTag(FiltersBSConstants.BOTTOM_SHEET_TEST_TAG)
    ) {
        HorizontalDivider(modifier = Modifier.padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(90.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag(FiltersBSConstants.FILTERS_LVG_TEST_TAG)
        ) {
            filterSection(
                releaseEnd = screenState.releaseEnd,
                onReleaseEndClick = { viewModel.sendIntent(SearchScreenIntent.ChangeReleaseEnd) }
            )

            sortSection(
                sort = screenState.sortedBy,
                onSortClick = { viewModel.sendIntent(SearchScreenIntent.ChangeSortedBy(it)) }
            )

            yearsSection(
                fromYear = screenState.fromYear,
                toYear = screenState.toYear,
                onFromYearChange = { viewModel.sendIntent(SearchScreenIntent.ChangeFromYear(it)) },
                onToYearChange = { viewModel.sendIntent(SearchScreenIntent.ChangeToYear(it)) },
            )

            seasonSection(
                seasons = screenState.animeSeasons,
                chosenSeasons = screenState.chosenSeasons,
                onSeasonClick = {
                    val currentSeasons = screenState.chosenSeasons.toMutableList()
                    viewModel.sendIntent(
                        SearchScreenIntent.ChangeChosenSeasons(
                            seasons = if (it in currentSeasons) currentSeasons - it else currentSeasons + it
                        )
                    )
                }
            )

            genresSection(
                genres = screenState.animeGenres,
                chosenGenres = screenState.chosenAnimeGenres,
                isLoading = screenState.isAnimeGenresLoading,
                isError = screenState.isAnimeGenresError,
                onGenresRetryClick = { viewModel.sendIntent(SearchScreenIntent.FetchAnimeGenres) },
                onGenreClick = {
                    val currentGenres = screenState.chosenAnimeGenres.toMutableList()
                    viewModel.sendIntent(
                        SearchScreenIntent.ChangeChosenAnimeGenres(
                            genres = if (it in currentGenres) currentGenres - it else currentGenres + it
                        )
                    )
                },
            )
        }
    }
}