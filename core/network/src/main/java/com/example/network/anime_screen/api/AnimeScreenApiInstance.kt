package com.example.network.anime_screen.api

import com.example.network.anime_screen.models.anime_response.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeScreenApiInstance {

    @GET("title")
    suspend fun getAnime(
        @Query("id") id: Int,
        @Query("filter") filter: String = "names,status,posters,type,genres,team,season,description,in_favorites,player,torrents"
    ): Response<AnimeResponse>
}