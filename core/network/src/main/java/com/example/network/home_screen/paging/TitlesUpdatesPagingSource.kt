package com.example.network.home_screen.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.home_screen.models.titles_updates_response.Item0
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

class TitlesUpdatesPagingSource(
    private val apiInstance: HomeScreenApiInstance,
): PagingSource<Int, Item0>() {

    override fun getRefreshKey(state: PagingState<Int, Item0>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item0> {
        val startPage = params.key ?: 1
        val perPage = params.loadSize

        val response = apiInstance.getTitlesUpdates(
            page = startPage,
            itemsPerPage = perPage
        )

        return try {
            if (response.code() == 200) {
                val body = response.body()!!
                LoadResult.Page(
                    data = body.list,
                    prevKey = if (body.pagination.currentPage > 1) body.pagination.currentPage - 1 else null,
                    nextKey = body.pagination.currentPage + 1
                )
            } else {
                val exception = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(exception)
                LoadResult.Error(NetworkException(exception, label))
            }
        } catch (e: IOException) {
            if ((e is UnknownHostException) or (e is SocketException)) {
                val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
                LoadResult.Error(NetworkException(NetworkErrors.INTERNET, label))
            } else {
                val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
                LoadResult.Error(NetworkException(NetworkErrors.UNAUTHORIZED, label))
            }
        }
    }
}