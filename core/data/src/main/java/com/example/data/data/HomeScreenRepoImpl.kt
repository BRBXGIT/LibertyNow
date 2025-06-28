package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.titles_list_response.Item0
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.home_screen.paging.TitlesByQueryPagingSource
import com.example.network.home_screen.paging.TitlesUpdatesPagingSource
import kotlinx.coroutines.flow.Flow
import java.net.SocketException
import java.net.UnknownHostException
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

    override suspend fun getRandomTitle(): NetworkResponse {
        return try {
            val response = apiInstance.getRandomTitle()

            if (response.code() == 200) {
                NetworkResponse(
                    response = response.body(),
                    error = NetworkErrors.SUCCESS
                )
            } else {
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)
                NetworkResponse(
                    error = error,
                    label = label
                )
            }
        } catch (e: Exception) {
            if ((e is UnknownHostException) or (e is SocketException)) {
                val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
                NetworkResponse(
                    error = NetworkErrors.INTERNET,
                    label = label
                )
            } else {
                val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
                NetworkResponse(
                    error = NetworkErrors.UNKNOWN,
                    label = label
                )
            }
        }
    }
}