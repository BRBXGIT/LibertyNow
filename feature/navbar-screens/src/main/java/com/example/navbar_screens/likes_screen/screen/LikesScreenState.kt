package com.example.navbar_screens.likes_screen.screen

data class LikesScreenState(
    val isAuthBSOpened: Boolean = false,
    val email: String = "",
    val password: String = "",

    val isLoading: Boolean = false
)
