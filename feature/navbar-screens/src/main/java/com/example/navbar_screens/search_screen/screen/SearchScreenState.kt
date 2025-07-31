package com.example.navbar_screens.search_screen.screen

import com.example.network.common.models.common.Genre
import java.time.LocalDateTime

enum class SortedBy {
    Popularity, Novelty
}

enum class Season {
    Winter, Spring, Summer, Autumn
}

data class SearchScreenState(
    val fromYear: Int = 0,
    val toYear: Int = LocalDateTime.now().year,

    val animeGenres: List<Genre> = emptyList(),
    val isAnimeGenresLoading: Boolean = true,
    val isAnimeGenresError: Boolean = false,
    val chosenAnimeGenres: List<Int> = emptyList(),

    val sortedBy: SortedBy = SortedBy.Popularity,

    val animeSeasons: List<Season> = listOf(Season.Winter, Season.Spring, Season.Summer, Season.Autumn),
    val chosenSeasons: List<Season> = emptyList(),

    val releaseEnd: Boolean = true,

    val isFilterBSOpened: Boolean = false,

    val isAnimeByFiltersLoading: Boolean = false
)
