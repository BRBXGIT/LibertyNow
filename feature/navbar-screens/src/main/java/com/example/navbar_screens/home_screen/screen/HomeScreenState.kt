package com.example.navbar_screens.home_screen.screen

data class HomeScreenState(
    val isLoading: Boolean = false,

    val query: String = "",
    val isSearching: Boolean = false
)
