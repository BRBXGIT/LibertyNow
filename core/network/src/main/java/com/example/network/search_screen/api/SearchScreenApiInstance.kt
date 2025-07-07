package com.example.network.search_screen.api

import com.example.network.common.models.TitlesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchScreenApiInstance {

    @GET("years")
    suspend fun getAnimeYears(): Response<List<Int>>

    @GET("genres")
    suspend fun getAnimeGenres(): Response<List<String>>

    @GET("title/search/advanced")
    suspend fun getAnimeByFilters(
        @Query("page") page: Int,
        @Query("items_per_page") itemsPerPage: Int,
        @Query("filter") filter: String = "id,posters,genres,names",
        @Query("sort_direction") sortDirection: Int = 1,
        @Query("order_by") orderBy: String,
        @Query("query") query: String
    ): Response<TitlesListResponse>
}