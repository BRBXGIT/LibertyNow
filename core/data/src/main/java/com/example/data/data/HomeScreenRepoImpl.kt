package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkRequest
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.home_screen.paging.TitlesByQueryPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeScreenRepoImpl @Inject constructor(
    private val apiInstance: HomeScreenApiInstance
): HomeScreenRepo {

    override suspend fun getTitlesUpdates() = NetworkRequest.exec { apiInstance.getTitlesUpdates() }

    override fun getTitlesByQuery(query: String): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { TitlesByQueryPagingSource(apiInstance, query) }
        ).flow
    }

    override suspend fun getRandomTitle() = NetworkRequest.exec { apiInstance.getRandomTitle() }
}