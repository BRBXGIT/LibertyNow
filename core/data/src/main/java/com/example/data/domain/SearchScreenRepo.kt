package com.example.data.domain

import androidx.paging.PagingData
import com.example.common.functions.NetworkResponse
import com.example.network.common.titles_list_response.Item0
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepo {

    suspend fun getAnimeYears(): NetworkResponse

    suspend fun getAnimeGenres(): NetworkResponse

    suspend fun getAnimeByFilters(
        query: String = "",
        orderBy: String
    ): Flow<PagingData<Item0>>
}