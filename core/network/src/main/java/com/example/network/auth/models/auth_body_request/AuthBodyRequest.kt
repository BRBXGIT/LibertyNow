package com.example.network.auth.models.auth_body_request


import com.google.gson.annotations.SerializedName

data class AuthBodyRequest(
    @SerializedName("login")
    val login: String = "",
    @SerializedName("password")
    val password: String = ""
)