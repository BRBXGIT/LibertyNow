package com.example.network.auth.api

import com.example.network.auth.models.auth_body_request.AuthBodyRequest
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInstance {

    @POST("accounts/users/auth/login")
    suspend fun getSessionToken(
        @Body authBody: AuthBodyRequest
    ): Response<SessionTokenResponse>
}