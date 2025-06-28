package com.example.data.data

import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.AnimeScreenRepo
import com.example.network.anime_screen.api.AnimeScreenApiInstance
import javax.inject.Inject

class AnimeScreenRepoImpl @Inject constructor(
    private val apiInstance: AnimeScreenApiInstance
): AnimeScreenRepo {

    override suspend fun getAnime(id: Int): NetworkResponse {
        return try {
            val response = apiInstance.getAnime(id)

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
}