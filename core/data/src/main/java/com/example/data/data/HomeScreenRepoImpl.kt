package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.HomeScreenRepo
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.common.titles_list_response.Item0
import com.example.network.home_screen.paging.TitlesByQueryPagingSource
import com.example.network.home_screen.paging.TitlesUpdatesPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeScreenRepoImpl @Inject constructor(
    private val apiInstance: HomeScreenApiInstance
): HomeScreenRepo {

    override fun getTitlesUpdates(): Flow<PagingData<Item0>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { TitlesUpdatesPagingSource(apiInstance) }
        ).flow
    }

    override fun getTitlesByQuery(query: String): Flow<PagingData<Item0>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { TitlesByQueryPagingSource(apiInstance, query) }
        ).flow
    }
}