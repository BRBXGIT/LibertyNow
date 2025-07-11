package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("optimized")
    val optimized: Optimized = Optimized(),
    @SerializedName("preview")
    val preview: Any? = null,
    @SerializedName("src")
    val src: Any? = null,
    @SerializedName("thumbnail")
    val thumbnail: Any? = null
)