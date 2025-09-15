package com.example.data.data

import com.example.data.domain.LikesRepo
import com.example.data.utils.DataUtils
import com.example.network.auth.api.LikesApiInstance
import com.example.network.auth.models.add_like_request.LikeRequestItem
import com.example.network.common.utils.NetworkRequest
import com.example.network.common.utils.NetworkResponse
import javax.inject.Inject

class LikesRepoImpl @Inject constructor(
    private val apiInstance: LikesApiInstance
): LikesRepo {

    override suspend fun getLikesAmount(sessionToken: String) =
        NetworkRequest.exec { apiInstance.getLikesAmount("${DataUtils.AUTHORIZATION_TYPE} $sessionToken") }

    override suspend fun getLikes(sessionToken: String, limit: Int) =
        NetworkRequest.exec { apiInstance.getLikes("${DataUtils.AUTHORIZATION_TYPE} $sessionToken", limit) }

    override suspend fun addLike(sessionToken: String, titleId: Int): NetworkResponse<Unit> {
        return NetworkRequest.exec {
            val requestBody = arrayListOf(LikeRequestItem(titleId))
            apiInstance.addLike("${DataUtils.AUTHORIZATION_TYPE} $sessionToken", requestBody)
        }
    }


    override suspend fun removeLike(sessionToken: String, titleId: Int): NetworkResponse<Unit> {
        return NetworkRequest.exec {
            val requestBody = arrayListOf(LikeRequestItem(titleId))
            apiInstance.removeLike("${DataUtils.AUTHORIZATION_TYPE} $sessionToken", requestBody)
        }
    }
}