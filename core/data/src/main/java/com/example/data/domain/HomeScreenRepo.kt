package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.common.titles_list_response.Item0
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    fun getTitlesUpdates(): Flow<PagingData<Item0>>

    fun getTitlesByQuery(query: String): Flow<PagingData<Item0>>
}