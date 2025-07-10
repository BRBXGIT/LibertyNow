package com.example.data.domain

import androidx.paging.PagingData
import com.example.common.functions.NetworkResponse
import com.example.network.common.models.Item0
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    suspend fun getTitlesUpdates(): NetworkResponse

    fun getTitlesByQuery(query: String): Flow<PagingData<Item0>>

    suspend fun getRandomTitle(): NetworkResponse
}