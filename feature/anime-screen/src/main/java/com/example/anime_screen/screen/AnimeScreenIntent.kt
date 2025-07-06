package com.example.anime_screen.screen

import com.example.local.db.lists_db.ListAnimeStatus

sealed interface AnimeScreenIntent {
    data class FetchAnime(val id: Int): AnimeScreenIntent
    data class ObserveWatchedEps(val id: Int): AnimeScreenIntent

    data class MoveAnimeToList(
        val id: Int,
        val poster: String,
        val genres: String,
        val name: String,
        val newStatus: ListAnimeStatus
    ): AnimeScreenIntent

    data class UpdateScreenState(val state: AnimeScreenState): AnimeScreenIntent
}