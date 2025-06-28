package com.example.data.data

import com.example.data.domain.AnimeScreenRepo
import com.example.network.anime_screen.api.AnimeScreenApiInstance
import com.example.network.anime_screen.models.anime_response.AnimeResponse
import retrofit2.Response
import javax.inject.Inject

class AnimeScreenRepoImpl @Inject constructor(
    private val apiInstance: AnimeScreenApiInstance
): AnimeScreenRepo {

    override suspend fun getAnime(id: Int): Response<AnimeResponse> {
        return apiInstance.getAnime(id)
    }
}