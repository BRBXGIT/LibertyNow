package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Opening(
    @SerializedName("start")
    val start: Int? = null,
    @SerializedName("stop")
    val stop: Int? = null
)