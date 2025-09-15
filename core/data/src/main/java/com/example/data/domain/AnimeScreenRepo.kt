package com.example.data.domain

import com.example.network.anime_screen.models.anime_details_response.AnimeDetailsResponse
import com.example.network.common.utils.NetworkResponse

interface AnimeScreenRepo {

    suspend fun getAnime(id: Int): NetworkResponse<AnimeDetailsResponse>
}