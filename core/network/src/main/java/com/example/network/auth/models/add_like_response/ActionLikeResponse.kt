package com.example.network.auth.models.add_like_response

import com.google.gson.annotations.SerializedName

// Action is add or remove title from likes
data class ActionLikeResponse(
    @SerializedName("success")
    val success: Boolean = false
)