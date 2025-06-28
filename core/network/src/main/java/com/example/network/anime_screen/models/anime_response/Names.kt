package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Names(
    @SerializedName("alternative")
    val alternative: String? = null,
    @SerializedName("en")
    val en: String = "",
    @SerializedName("ru")
    val ru: String = ""
)