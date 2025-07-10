package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("alternative")
    val alternative: String? = null,
    @SerializedName("english")
    val english: String = "",
    @SerializedName("main")
    val main: String = ""
)