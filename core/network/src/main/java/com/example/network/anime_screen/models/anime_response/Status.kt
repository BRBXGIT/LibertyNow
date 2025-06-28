package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("string")
    val string: String = ""
)