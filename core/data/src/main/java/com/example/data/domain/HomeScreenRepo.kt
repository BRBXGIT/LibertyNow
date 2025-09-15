package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.common.models.anime_list_response.AnimeListResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    suspend fun getTitlesUpdates(): NetworkResponse<AnimeListResponse>

    fun getTitlesByQuery(query: String): Flow<PagingData<Data>>

    suspend fun getRandomTitle(): NetworkResponse<AnimeListResponse>
}