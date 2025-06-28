package com.example.data.domain

import com.example.network.anime_screen.models.anime_response.AnimeResponse
import retrofit2.Response

interface AnimeScreenRepo {

    suspend fun getAnime(id: Int): Response<AnimeResponse>
}