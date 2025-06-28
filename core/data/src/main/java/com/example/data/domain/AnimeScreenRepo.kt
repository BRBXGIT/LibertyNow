package com.example.data.domain

import com.example.common.functions.NetworkResponse

interface AnimeScreenRepo {

    suspend fun getAnime(id: Int): NetworkResponse
}