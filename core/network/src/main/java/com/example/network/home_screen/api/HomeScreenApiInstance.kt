package com.example.network.home_screen.api

import com.example.network.home_screen.models.titles_updates_response.TitlesUpdatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeScreenApiInstance {

    @GET("title/updates")
    suspend fun getTitlesUpdates(
        @Query("filter") filter: String = "posters,genres,names",
        @Query("page") page: Int,
        @Query("items_per_page") itemsPerPage: Int
    ): Response<TitlesUpdatesResponse>
}