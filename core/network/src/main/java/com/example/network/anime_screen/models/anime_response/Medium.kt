package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Medium(
    @SerializedName("raw_base64_file")
    val rawBase64File: String? = null,
    @SerializedName("url")
    val url: String = ""
)