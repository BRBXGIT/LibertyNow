package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("value")
    val value: String = ""
)