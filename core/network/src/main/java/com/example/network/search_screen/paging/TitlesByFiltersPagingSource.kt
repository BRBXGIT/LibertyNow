package com.example.network.search_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptionsForPaging
import com.example.network.common.titles_list_response.Item0
import com.example.network.search_screen.api.SearchScreenApiInstance
import java.io.IOException

class TitlesByFiltersPagingSource(
    private val apiInstance: SearchScreenApiInstance,
    private val query: String,
    private val orderBy: String
): PagingSource<Int, Item0>() {

    override fun getRefreshKey(state: PagingState<Int, Item0>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item0> {
        val startPage = params.key ?: 1
        val perPage = params.loadSize

        return try {
            val response = apiInstance.getAnimeByFilters(
                page = startPage,
                itemsPerPage = perPage,
                query = query,
                orderBy = orderBy
            )

            if (response.code() == 200) {
                val body = response.body()
                if (body != null) {
                    LoadResult.Page(
                        data = body.list,
                        prevKey = if (body.pagination.currentPage > 1) body.pagination.currentPage - 1 else null,
                        nextKey = body.pagination.currentPage + 1
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
            processNetworkExceptionsForPaging<Int, Item0>(e)
        }
    }
}