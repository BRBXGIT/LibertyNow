package com.example.network.auth.api

import com.example.network.auth.models.add_like_request.LikeRequestItem
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LikesApiInstance {

    @GET("accounts/users/me/favorites/ids")
    suspend fun getLikesAmount(
        @Header("Authorization") sessionToken: String,
    ): Response<List<Int>>

    @GET("accounts/users/me/favorites/releases")
    suspend fun getLikes(
        @Header("Authorization") sessionToken: String,
        @Query("limit") limit: Int
    ): Response<AnimeListWithPaginationResponse>

    @POST("accounts/users/me/favorites")
    suspend fun addLike(
        @Header("Authorization") sessionToken: String,
        @Body addLikeRequest: ArrayList<LikeRequestItem>
    ): Response<List<Int>>

    @HTTP(method = "DELETE", path = "accounts/users/me/favorites", hasBody = true)
    suspend fun removeLike(
        @Header("Authorization") sessionToken: String,
        @Body removeLikeRequest: ArrayList<LikeRequestItem>
    ): Response<List<Int>>
}