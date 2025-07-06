package com.example.anime_screen.screen

import com.example.network.anime_screen.models.anime_response.AnimeResponse

data class AnimeScreenState(
    val anime: AnimeResponse? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,

    val isDescriptionExpanded: Boolean = false,
    val scrollDirection: ScrollDirection = ScrollDirection.Up,

    val isInLikes: Boolean = false,

    val watchedEps: List<Int> = emptyList(),

    val isListsBSOpened: Boolean = false
)