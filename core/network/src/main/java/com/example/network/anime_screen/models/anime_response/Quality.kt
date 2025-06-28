package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class Quality(
    @SerializedName("encoder")
    val encoder: String = "",
    @SerializedName("lq_audio")
    val lqAudio: Any? = null,
    @SerializedName("resolution")
    val resolution: String = "",
    @SerializedName("string")
    val string: String = "",
    @SerializedName("type")
    val type: String = ""
)