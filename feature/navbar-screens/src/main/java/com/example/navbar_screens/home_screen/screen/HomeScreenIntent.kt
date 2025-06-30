package com.example.navbar_screens.home_screen.screen

sealed interface HomeScreenIntent {
    data class UpdateScreenState(val state: HomeScreenState): HomeScreenIntent
    data class FetchRandomTitle(val onComplete: (Int) -> Unit): HomeScreenIntent
}