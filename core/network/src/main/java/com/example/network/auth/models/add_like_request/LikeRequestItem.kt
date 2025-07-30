package com.example.network.auth.models.add_like_request


import com.google.gson.annotations.SerializedName

data class LikeRequestItem(
    @SerializedName("release_id")
    val releaseId: Int = 0
)