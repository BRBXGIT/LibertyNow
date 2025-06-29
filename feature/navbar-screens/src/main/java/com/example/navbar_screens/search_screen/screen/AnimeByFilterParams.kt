package com.example.navbar_screens.search_screen.screen

data class AnimeByFilterParams(
    val chosenAnimeYears: List<Int> = emptyList(),
    val chosenAnimeGenres: List<String> = emptyList(),
    val sortedBy: SortedBy = SortedBy.Popularity,
    val chosenSeasons: List<Season> = emptyList(),
    val releaseEnd: Boolean = false
)