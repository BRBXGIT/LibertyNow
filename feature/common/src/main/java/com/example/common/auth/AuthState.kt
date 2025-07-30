package com.example.common.auth

import com.example.local.datastore.auth.LoggingState
import com.example.network.common.models.anime_list_with_pagination_response.Data

data class AuthState(
    val isLogged: LoggingState = LoggingState.Loading,
    val sessionToken: String? = null,
    val isLoading: Boolean = false,

    val isAuthBSOpened: Boolean = false,
    val email: String = "",
    val password: String = "",
    val incorrectEmail: Boolean = false,
    val incorrectPassword: Boolean = false,
    val isPasswordVisible: Boolean = true,

    val likesAmount: Int = 0,
    val likesError: Boolean = false,
    val likes: List<Data> = emptyList()
)
