package com.example.network.home_screen.api

import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.home_screen.models.RandomTitleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeScreenApiInstance {

    @GET("anime/releases/latest")
    suspend fun getTitlesUpdates(
        @Query("limit") limit: Int = 30
    ): Response<AnimeListResponse>

    @GET("anime/catalog/releases")
    suspend fun getTitlesByQuery(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<AnimeListWithPaginationResponse>

    @GET("title/random")
    suspend fun getRandomTitle(
        @Query("filter") filter: String = "id"
    ): Response<RandomTitleResponse>
}