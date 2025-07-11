package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("optimized")
    val optimized: Optimized = Optimized(),
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)