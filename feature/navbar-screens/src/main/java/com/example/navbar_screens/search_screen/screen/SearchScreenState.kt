package com.example.navbar_screens.search_screen.screen

enum class SortedBy {
    Popularity, Novelty
}

enum class Season {
    Winter, Spring, Summer, Autumn
}

data class SearchScreenState(
    val animeYears: List<Int> = emptyList(),
    val isAnimeYearsLoading: Boolean = false,
    val isAnimeYearsError: Boolean = false,
    val chosenAnimeYears: List<Int> = emptyList(),

    val animeGenres: List<String> = emptyList(),
    val isAnimeGenresLoading: Boolean = false,
    val isAnimeGenresError: Boolean = false,
    val chosenAnimeGenres: List<String> = emptyList(),

    val sortedBy: SortedBy = SortedBy.Popularity,

    val chosenSeasons: List<Season> = emptyList(),

    val releaseEnd: Boolean = false
)
