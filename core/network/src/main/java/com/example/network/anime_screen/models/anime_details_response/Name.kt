package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("alternative")
    val alternative: String = "",
    @SerializedName("english")
    val english: String = "",
    @SerializedName("main")
    val main: String = ""
)