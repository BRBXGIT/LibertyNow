package com.example.anime_screen.screen

sealed class AnimeScreenIntent {
    data class FetchAnime(val id: Int): AnimeScreenIntent()
    data class UpdateScreenState(val state: AnimeScreenState): AnimeScreenIntent()
}