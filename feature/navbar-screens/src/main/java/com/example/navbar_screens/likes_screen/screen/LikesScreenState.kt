package com.example.navbar_screens.likes_screen.screen

import com.example.local.datastore.auth.AuthState

data class LikesScreenState(
    val isUserLoggedIn: AuthState = AuthState.Loading,
    val sessionToken: String? = null,

    val isAuthBSOpened: Boolean = false,
    val email: String = "",
    val password: String = "",

    val isLoading: Boolean = false
)
