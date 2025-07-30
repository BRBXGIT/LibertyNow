package com.example.network.auth.models.session_token_response

import com.google.gson.annotations.SerializedName

data class SessionTokenResponse(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("error")
    val error: String = "",
)