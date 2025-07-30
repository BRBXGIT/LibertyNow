package com.example.data.data

import android.util.Log
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.LikesRepo
import com.example.data.utils.DataUtils
import com.example.network.auth.api.LikesApiInstance
import com.example.network.auth.models.add_like_request.LikeRequestItem
import javax.inject.Inject

class LikesRepoImpl @Inject constructor(
    private val apiInstance: LikesApiInstance
): LikesRepo {

    override suspend fun getLikesAmount(sessionToken: String): NetworkResponse {
        return try {
            val response = apiInstance.getLikesAmount("${DataUtils.AUTHORIZATION_TYPE} $sessionToken")

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

    override suspend fun getLikes(sessionToken: String, limit: Int): NetworkResponse {
        return try {
            val response = apiInstance.getLikes(
                sessionToken = "${DataUtils.AUTHORIZATION_TYPE} $sessionToken",
                limit = limit
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
            val requestBody = arrayListOf<LikeRequestItem>(LikeRequestItem(titleId))
            val response = apiInstance.addLike("${DataUtils.AUTHORIZATION_TYPE} $sessionToken", requestBody)

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
            val requestBody = arrayListOf<LikeRequestItem>(LikeRequestItem(titleId))
            val response = apiInstance.removeLike("${DataUtils.AUTHORIZATION_TYPE} $sessionToken", requestBody)

            if (response.code() == 200) {
                NetworkResponse(
                    response = response.body(),
                    error = NetworkErrors.SUCCESS
                )
            } else {
                Log.d("CCCC", response.code().toString())
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)
                NetworkResponse(
                    error = error,
                    label = label
                )
            }
        } catch (e: Exception) {
            Log.d("CCCC", e.toString())
            processNetworkExceptions(e)
        }
    }
}