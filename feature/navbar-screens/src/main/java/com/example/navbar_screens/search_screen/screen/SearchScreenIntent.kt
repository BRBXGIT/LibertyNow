package com.example.navbar_screens.search_screen.screen

sealed interface SearchScreenIntent {

    // === Data ===
    data object FetchAnimeGenres: SearchScreenIntent

    // === States ===
    data object ChangeAnimeByFiltersLoading: SearchScreenIntent
    data object ChangeFiltersBSVisible: SearchScreenIntent
    data object ChangeReleaseEnd: SearchScreenIntent
    data class ChangeSortedBy(val sortedBy: SortedBy): SearchScreenIntent
    data class ChangeChosenSeasons(val seasons: List<Season>): SearchScreenIntent
    data class ChangeChosenAnimeGenres(val genres: List<Int>): SearchScreenIntent
    data class ChangeFromYear(val year: Int): SearchScreenIntent
    data class ChangeToYear(val year: Int): SearchScreenIntent
}