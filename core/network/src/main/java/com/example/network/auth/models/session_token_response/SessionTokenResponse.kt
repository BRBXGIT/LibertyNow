package com.example.network.auth.models.session_token_response

import com.google.gson.annotations.SerializedName

data class SessionTokenResponse(
    @SerializedName("err")
    val err: String = "",
    @SerializedName("key")
    val key: String = "",
    @SerializedName("mes")
    val mes: String = "",
    @SerializedName("sessionId")
    val sessionId: String = ""
)