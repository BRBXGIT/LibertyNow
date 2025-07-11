package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class Ending(
    @SerializedName("start")
    val start: Int? = null,
    @SerializedName("stop")
    val stop: Int? = null
)