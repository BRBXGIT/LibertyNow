package com.example.network.common.models.anime_list_with_pagination_response


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("optimized")
    val optimized: Optimized = Optimized(),
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("thumbnail")
    val thumbnail: String = ""
)