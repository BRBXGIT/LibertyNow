package com.example.data.data

import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.LikesRepo
import com.example.network.auth.api.LikesApiInstance
import javax.inject.Inject

class LikesRepoImpl @Inject constructor(
    private val apiInstance: LikesApiInstance
): LikesRepo {

    override suspend fun getLikesAmount(sessionToken: String): NetworkResponse {
        return try {
            val response = apiInstance.getLikesAmount(sessionToken)

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

    override suspend fun getLikes(sessionToken: String, itemsPerPage: Int): NetworkResponse {
        return try {
            val response = apiInstance.getLikes(
                sessionToken = sessionToken,
                itemsPerPage = itemsPerPage
            )

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

    override suspend fun addLike(sessionToken: String, titleId: Int): NetworkResponse {
        return try {
            val response = apiInstance.addLike(sessionToken, titleId)

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

    override suspend fun removeLike(sessionToken: String, titleId: Int): NetworkResponse {
        return try {
            val response = apiInstance.removeLike(sessionToken, titleId)

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