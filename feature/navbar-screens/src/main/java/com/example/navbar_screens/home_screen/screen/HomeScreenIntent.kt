package com.example.navbar_screens.home_screen.screen

sealed class HomeScreenIntent {
    data class UpdateScreenState(val state: HomeScreenState): HomeScreenIntent()
}