package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class OptimizedX(
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("src")
    val src: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)