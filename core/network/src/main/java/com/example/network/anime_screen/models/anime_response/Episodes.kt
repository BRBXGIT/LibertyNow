package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Episodes(
    @SerializedName("first")
    val first: Int = 0,
    @SerializedName("last")
    val last: Int = 0,
    @SerializedName("string")
    val string: String = ""
)