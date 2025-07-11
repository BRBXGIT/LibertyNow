package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("description")
    val description: Any? = null,
    @SerializedName("value")
    val value: Any? = null
)