package com.example.network.search_screen.api

import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.models.common.Genre
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SearchScreenApiInstance {

    @GET("years")
    suspend fun getAnimeYears(): Response<List<Int>>

    @GET("anime/genres")
    suspend fun getAnimeGenres(): Response<List<Genre>>

    @POST("anime/catalog/releases")
    suspend fun getAnimeByFilters(
        @Body animeByFiltersRequest: AnimeByFiltersRequest
    ): Response<AnimeListWithPaginationResponse>
}