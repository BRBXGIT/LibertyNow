package com.example.network.common.models.anime_list_with_pagination_response


import com.google.gson.annotations.SerializedName

data class AnimeListWithPaginationResponse(
    @SerializedName("data")
    val data: List<Data> = listOf(),
    @SerializedName("meta")
    val meta: Meta = Meta()
)