package com.example.network.anime_screen.models.anime_response

import com.google.gson.annotations.SerializedName

data class Hls(
    @SerializedName("fhd")
    val fhd: String = "",
    @SerializedName("hd")
    val hd: String = "",
    @SerializedName("sd")
    val sd: String = ""
)