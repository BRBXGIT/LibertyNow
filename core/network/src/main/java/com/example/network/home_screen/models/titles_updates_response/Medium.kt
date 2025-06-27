package com.example.network.home_screen.models.titles_updates_response


import com.google.gson.annotations.SerializedName

data class Medium(
    @SerializedName("raw_base64_file")
    val rawBase64File: Any? = null,
    @SerializedName("url")
    val url: String = ""
)