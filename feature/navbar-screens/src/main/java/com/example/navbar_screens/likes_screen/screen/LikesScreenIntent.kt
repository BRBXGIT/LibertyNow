package com.example.navbar_screens.likes_screen.screen

sealed interface LikesScreenIntent {
    data class UpdateScreenState(val state: LikesScreenState): LikesScreenIntent
}