package com.example.network.home_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkException
import com.example.network.common.utils.NetworkRequest
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.search_screen.models.anime_by_filters_request.AnimeByFiltersRequest
import com.example.network.search_screen.models.anime_by_filters_request.F

class TitlesByQueryPagingSource(
    private val apiInstance: HomeScreenApiInstance,
    private val query: String
): PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val startPage = params.key ?: 1
        val perPage = params.loadSize

        val request = AnimeByFiltersRequest(
            limit = perPage,
            page = startPage,
            f = F(
                search = query
            )
        )

        val response = NetworkRequest.exec { apiInstance.getTitlesByQuery(request) }
        return if (response.error == NetworkErrors.SUCCESS) {
            val result = response.response!!
            LoadResult.Page(
                data = result.data,
                prevKey = if (result.meta.pagination.currentPage > 1) result.meta.pagination.currentPage - 1 else null,
                nextKey = result.meta.pagination.currentPage + 1
            )
        } else {
            LoadResult.Error(NetworkException(response.error!!, response.label!!))
        }
    }
}