package com.example.data.domain

import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.utils.NetworkResponse

interface LikesRepo {

    suspend fun getLikesAmount(sessionToken: String): NetworkResponse<List<Int>>

    suspend fun getLikes(
        sessionToken: String,
        limit: Int
    ): NetworkResponse<AnimeListWithPaginationResponse>

    suspend fun addLike(
        sessionToken: String,
        titleId: Int
    ): NetworkResponse<Unit>

    suspend fun removeLike(
        sessionToken: String,
        titleId: Int
    ): NetworkResponse<Unit>
}