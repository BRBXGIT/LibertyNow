package com.example.data.domain

import androidx.paging.PagingData
import com.example.common.functions.NetworkResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import kotlinx.coroutines.flow.Flow

interface SearchScreenRepo {

    suspend fun getAnimeYears(): NetworkResponse

    suspend fun getAnimeGenres(): NetworkResponse

    suspend fun getAnimeByFilters(
        requestBody: AnimeByFiltersRequest
    ): Flow<PagingData<Data>>
}