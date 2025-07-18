package com.example.anime_screen.screen

import com.example.local.db.lists_db.ListAnimeStatus
import com.example.network.anime_screen.models.anime_details_response.AnimeDetailsResponse

data class AnimeScreenState(
    val animeId: Int = 0,
    val anime: AnimeDetailsResponse? = null,
    val isLoading: Boolean = true,
    val isError: Boolean = false,

    val isDescriptionExpanded: Boolean = false,
    val scrollDirection: ScrollDirection = ScrollDirection.Up,

    val isInLikes: Boolean = false,

    val watchedEps: List<Int> = emptyList(),

    val isListsBSOpened: Boolean = false,
    val currentListsAnimeIn: List<ListAnimeStatus> = emptyList()
)