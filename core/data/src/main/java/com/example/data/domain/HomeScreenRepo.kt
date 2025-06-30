package com.example.data.domain

import androidx.paging.PagingData
import com.example.common.functions.NetworkResponse
import com.example.network.common.titles_list_response.Item0
import com.example.network.home_screen.models.RandomTitleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeScreenRepo {

    fun getTitlesUpdates(): Flow<PagingData<Item0>>

    fun getTitlesByQuery(query: String): Flow<PagingData<Item0>>

    suspend fun getRandomTitle(): NetworkResponse
}