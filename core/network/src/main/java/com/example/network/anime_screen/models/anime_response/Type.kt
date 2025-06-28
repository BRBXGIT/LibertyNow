package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("episodes")
    val episodes: Int = 0,
    @SerializedName("full_string")
    val fullString: String = "",
    @SerializedName("length")
    val length: Int = 0,
    @SerializedName("string")
    val string: String = ""
)