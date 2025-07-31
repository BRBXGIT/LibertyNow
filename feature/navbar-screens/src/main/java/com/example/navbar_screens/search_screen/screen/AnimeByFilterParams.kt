package com.example.navbar_screens.search_screen.screen

import java.time.LocalDateTime

data class AnimeByFilterParams(
    val fromYear: Int = 0,
    val toYear: Int = LocalDateTime.now().year,
    val chosenAnimeGenres: List<Int> = emptyList(),
    val sortedBy: SortedBy = SortedBy.Popularity,
    val chosenSeasons: List<Season> = emptyList(),
    val releaseEnd: Boolean = false
)