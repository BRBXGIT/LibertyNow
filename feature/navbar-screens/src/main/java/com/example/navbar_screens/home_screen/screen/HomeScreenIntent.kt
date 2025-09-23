package com.example.navbar_screens.home_screen.screen

sealed interface HomeScreenIntent {
    data class FetchRandomTitle(val onComplete: (Int) -> Unit): HomeScreenIntent

    data object ChangeIsLoading: HomeScreenIntent
    data object ChangeIsSearching: HomeScreenIntent
    data class ChangeQuery(val query: String): HomeScreenIntent
}