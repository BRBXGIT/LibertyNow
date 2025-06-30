package com.example.network.auth.api

import com.example.network.auth.models.likes_amount_response.LikesAmountResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LikesApiInstance {

    @GET("user/favorites")
    suspend fun getLikesAmount(
        @Query("session") sessionToken: String,
        @Query("filter") filter: String = "pagination"
    ): Response<LikesAmountResponse>
}