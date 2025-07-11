package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("optimized")
    val optimized: OptimizedX = OptimizedX(),
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)