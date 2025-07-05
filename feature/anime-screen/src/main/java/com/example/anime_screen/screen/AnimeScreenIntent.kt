package com.example.anime_screen.screen

sealed interface AnimeScreenIntent {
    data class FetchAnime(val id: Int): AnimeScreenIntent
    data class ObserveWatchedEps(val id: Int): AnimeScreenIntent

    data class UpdateScreenState(val state: AnimeScreenState): AnimeScreenIntent
}