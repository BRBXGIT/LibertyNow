package com.example.network.home_screen.api

import com.example.network.common.models.TitlesListResponse
import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.home_screen.models.RandomTitleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeScreenApiInstance {

    @GET("anime/releases/latest")
    suspend fun getTitlesUpdates(
        @Query("limit") limit: Int = 30
    ): Response<AnimeListResponse>

    @GET("title/search")
    suspend fun getTitlesByQuery(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("items_per_page") itemsPerPage: Int,
        @Query("filter") filter: String = "id,posters,genres,names"
    ): Response<TitlesListResponse>

    @GET("title/random")
    suspend fun getRandomTitle(
        @Query("filter") filter: String = "id"
    ): Response<RandomTitleResponse>
}