package com.example.network.common.models.anime_list_with_pagination_response


import com.google.gson.annotations.SerializedName

data class Optimized(
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)