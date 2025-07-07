package com.example.network.auth.api

import com.example.network.auth.models.add_like_response.ActionLikeResponse
import com.example.network.auth.models.likes_amount_response.LikesAmountResponse
import com.example.network.common.models.TitlesListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface LikesApiInstance {

    @GET("user/favorites")
    suspend fun getLikesAmount(
        @Query("session") sessionToken: String,
        @Query("filter") filter: String = "pagination"
    ): Response<LikesAmountResponse>

    @GET("user/favorites")
    suspend fun getLikes(
        @Query("session") sessionToken: String,
        @Query("filter") filter: String = "id,posters,genres,names",
        @Query("items_per_page") itemsPerPage: Int
    ): Response<TitlesListResponse>

    @PUT("user/favorites")
    suspend fun addLike(
        @Query("session") sessionToken: String,
        @Query("title_id") titleId: Int
    ): Response<ActionLikeResponse>

    @DELETE("user/favorites")
    suspend fun removeLike(
        @Query("session") sessionToken: String,
        @Query("title_id") titleId: Int
    ): Response<ActionLikeResponse>
}