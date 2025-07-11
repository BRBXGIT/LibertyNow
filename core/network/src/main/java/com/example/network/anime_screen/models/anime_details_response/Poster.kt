package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("optimized")
    val optimized: OptimizedXX = OptimizedXX(),
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("src")
    val src: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)