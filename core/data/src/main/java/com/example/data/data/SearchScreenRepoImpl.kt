package com.example.data.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.SearchScreenRepo
import com.example.network.common.titles_list_response.Item0
import com.example.network.search_screen.api.SearchScreenApiInstance
import com.example.network.search_screen.paging.TitlesByFiltersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchScreenRepoImpl @Inject constructor(
    private val apiInstance: SearchScreenApiInstance
): SearchScreenRepo {

    override suspend fun getAnimeYears(): NetworkResponse {
        return try {
            val response = apiInstance.getAnimeYears()

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
            processNetworkExceptions(e)
        }
    }

    override suspend fun getAnimeGenres(): NetworkResponse {
        return try {
            val response = apiInstance.getAnimeGenres()

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
            processNetworkExceptions(e)
        }
    }

    override suspend fun getAnimeByFilters(
        query: String,
        orderBy: String
    ): Flow<PagingData<Item0>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { TitlesByFiltersPagingSource(apiInstance, query, orderBy) }
        ).flow
    }
}