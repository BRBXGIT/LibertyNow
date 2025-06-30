package com.example.network.auth.api

import com.example.network.auth.models.likes_amount_response.LikesAmountResponse
import com.example.network.common.titles_list_response.TitlesListResponse
import retrofit2.Response
import retrofit2.http.GET
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
}