package com.example.data.domain

import androidx.paging.PagingData
import com.example.common.functions.NetworkResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    suspend fun getTitlesUpdates(): NetworkResponse

    fun getTitlesByQuery(query: String): Flow<PagingData<Data>>

    suspend fun getRandomTitle(): NetworkResponse
}