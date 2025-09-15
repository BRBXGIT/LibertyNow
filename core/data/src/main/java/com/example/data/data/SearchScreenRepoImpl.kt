package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkRequest
import com.example.network.search_screen.api.SearchScreenApiInstance
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import com.example.network.search_screen.paging.TitlesByFiltersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchScreenRepoImpl @Inject constructor(
    private val apiInstance: SearchScreenApiInstance
): SearchScreenRepo {

    override suspend fun getAnimeGenres() =
        NetworkRequest.exec { apiInstance.getAnimeGenres() }

    override suspend fun getAnimeByFilters(
        requestBody: AnimeByFiltersRequest
    ): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { TitlesByFiltersPagingSource(apiInstance, requestBody) }
        ).flow
    }
}