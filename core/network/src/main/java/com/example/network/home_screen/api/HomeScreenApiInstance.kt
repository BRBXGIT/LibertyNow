package com.example.network.home_screen.api

import com.example.network.common.models.TitlesListResponse
import com.example.network.home_screen.models.RandomTitleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeScreenApiInstance {

    @GET("title/updates")
    suspend fun getTitlesUpdates(
        @Query("filter") filter: String = "id,posters,genres,names",
        @Query("page") page: Int,
        @Query("items_per_page") itemsPerPage: Int
    ): Response<TitlesListResponse>

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