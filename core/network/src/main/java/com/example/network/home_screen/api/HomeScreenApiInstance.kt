package com.example.network.home_screen.api

import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeScreenApiInstance {

    @GET("anime/releases/latest")
    suspend fun getTitlesUpdates(
        @Query("limit") limit: Int = 30
    ): Response<AnimeListResponse>

    @POST("anime/catalog/releases")
    suspend fun getTitlesByQuery(
        @Body animeByTitleRequest: AnimeByFiltersRequest
    ): Response<AnimeListWithPaginationResponse>

    @GET("anime/releases/random")
    suspend fun getRandomTitle(
        @Query("filter") filter: String = "id"
    ): Response<AnimeListResponse>
}