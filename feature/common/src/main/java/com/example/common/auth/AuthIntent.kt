package com.example.common.auth

import com.example.network.common.models.anime_list_with_pagination_response.Data

sealed interface AuthIntent {
    data object GetSessionToken: AuthIntent
    data object ClearSessionToken: AuthIntent

    data class UpdateAuthState(val state: AuthState): AuthIntent

    data class AddLike(val title: Data): AuthIntent
    data class RemoveLike(val title: Data): AuthIntent
}