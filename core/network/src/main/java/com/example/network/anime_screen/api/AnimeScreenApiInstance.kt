package com.example.network.anime_screen.api

import com.example.network.anime_screen.models.anime_details_response.AnimeDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeScreenApiInstance {

    @GET("anime/releases/{id}")
    suspend fun getAnime(
        @Path("id") id: Int,
    ): Response<AnimeDetailsResponse>
}