package com.example.network.home_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptionsForPaging
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.home_screen.api.HomeScreenApiInstance
import java.io.IOException

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

        return try {
            val response = apiInstance.getTitlesByQuery(
                page = startPage,
                limit = perPage,
                query = query
            )

            if (response.code() == 200) {
                val body = response.body()
                if (body != null) {
                    LoadResult.Page(
                        data = body.data,
                        prevKey = if (body.meta.pagination.currentPage > 1) body.meta.pagination.currentPage - 1 else null,
                        nextKey = body.meta.pagination.currentPage + 1
                    )
                } else {
                    val label = processNetworkErrorsForUi(NetworkErrors.SERIALIZATION)
                    LoadResult.Error(NetworkException(NetworkErrors.SERIALIZATION, label))
                }
            } else {
                val exception = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(exception)
                LoadResult.Error(NetworkException(exception, label))
            }
        } catch (e: IOException) {
            processNetworkExceptionsForPaging<Int, Data>(e)
        }
    }
}