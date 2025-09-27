package com.example.navbar_screens.likes_screen.screen

sealed interface LikesScreenIntent {
    data object ChangeIsSearching: LikesScreenIntent

    data class ChangeQuery(val query: String): LikesScreenIntent
}