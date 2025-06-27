package com.example.data.domain

import androidx.paging.PagingData
import com.example.network.home_screen.models.titles_updates_response.Item0
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {

    fun getTitlesUpdates(): Flow<PagingData<Item0>>
}