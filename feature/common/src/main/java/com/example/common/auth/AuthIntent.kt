package com.example.common.auth

import com.example.network.common.titles_list_response.Item0

sealed interface AuthIntent {
    data object GetSessionToken: AuthIntent
    data class UpdateAuthState(val state: AuthState): AuthIntent

    data class AddLike(val title: Item0): AuthIntent
    data class RemoveLike(val title: Item0): AuthIntent
}