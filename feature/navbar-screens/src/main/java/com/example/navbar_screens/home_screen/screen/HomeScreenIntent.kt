package com.example.navbar_screens.home_screen.screen

sealed interface HomeScreenIntent {
    data class FetchRandomTitle(val onComplete: (Int) -> Unit): HomeScreenIntent

    data class ChangeIsLoading(val isLoading: Boolean): HomeScreenIntent
    data object ChangeIsSearching: HomeScreenIntent
    data class ChangeQuery(val query: String): HomeScreenIntent
}