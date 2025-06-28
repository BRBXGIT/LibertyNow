package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class X1(
    @SerializedName("created_timestamp")
    val createdTimestamp: Int = 0,
    @SerializedName("episode")
    val episode: Int = 0,
    @SerializedName("hls")
    val hls: Hls = Hls(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("skips")
    val skips: Skips = Skips(),
    @SerializedName("uuid")
    val uuid: String = ""
)