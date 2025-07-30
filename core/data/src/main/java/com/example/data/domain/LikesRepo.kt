package com.example.data.domain

import com.example.common.functions.NetworkResponse

interface LikesRepo {

    suspend fun getLikesAmount(sessionToken: String): NetworkResponse

    suspend fun getLikes(
        sessionToken: String,
        limit: Int
    ): NetworkResponse

    suspend fun addLike(
        sessionToken: String,
        titleId: Int
    ): NetworkResponse

    suspend fun removeLike(
        sessionToken: String,
        titleId: Int
    ): NetworkResponse
}