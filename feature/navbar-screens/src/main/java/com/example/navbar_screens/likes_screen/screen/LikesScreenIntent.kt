package com.example.navbar_screens.likes_screen.screen

sealed class LikesScreenIntent {
    data class UpdateScreenState(val state: LikesScreenState): LikesScreenIntent()
}