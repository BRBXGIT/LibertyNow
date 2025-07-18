package com.example.navbar_screens.search_screen.screen

sealed interface SearchScreenIntent {
    data object FetchAnimeYears: SearchScreenIntent
    data object FetchAnimeGenres: SearchScreenIntent

    data class UpdateScreenState(val state: SearchScreenState): SearchScreenIntent
}