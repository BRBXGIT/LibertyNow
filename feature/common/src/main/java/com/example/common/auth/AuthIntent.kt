package com.example.common.auth

import com.example.network.common.models.anime_list_with_pagination_response.Data

sealed interface AuthIntent {
    // Tokens
    data object GetSessionToken: AuthIntent
    data object ClearSessionToken: AuthIntent

    // Ui state
    data object ChangeIsAuthBsOpened: AuthIntent
    data class ChangePassword(val password: String): AuthIntent
    data class ChangeEmail(val email: String): AuthIntent
    data object ChangeIsPasswordVisible: AuthIntent

    // Likes
    data class AddLike(val title: Data): AuthIntent
    data class RemoveLike(val title: Data): AuthIntent
}