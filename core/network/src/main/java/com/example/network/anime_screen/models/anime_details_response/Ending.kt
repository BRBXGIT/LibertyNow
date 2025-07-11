package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Ending(
    @SerializedName("start")
    val start: Any? = null,
    @SerializedName("stop")
    val stop: Any? = null
)