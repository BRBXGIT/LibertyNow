package com.example.navbar_screens.home_screen.screen

import com.example.network.common.models.anime_list_response.AnimeListResponse

data class HomeScreenState(
    val titlesUpdates: AnimeListResponse = AnimeListResponse(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val query: String = "",
    val isSearching: Boolean = false,
)
