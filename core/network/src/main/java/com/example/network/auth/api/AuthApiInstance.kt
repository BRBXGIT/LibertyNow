package com.example.network.auth.api

import com.example.network.auth.models.session_token_response.SessionTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiInstance {

    @FormUrlEncoded
    @POST("public/login.php")
    suspend fun getSessionToken(
        @Field("mail") email: String,
        @Field("passwd") password: String
    ): Response<SessionTokenResponse>
}