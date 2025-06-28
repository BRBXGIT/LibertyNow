package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("string")
    val string: String = "",
    @SerializedName("week_day")
    val weekDay: Int = 0,
    @SerializedName("year")
    val year: Int = 0
)