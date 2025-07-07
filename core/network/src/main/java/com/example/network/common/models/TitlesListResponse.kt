package com.example.network.common.models


import com.google.gson.annotations.SerializedName

data class TitlesListResponse(
    @SerializedName("list")
    val list: List<Item0> = listOf(),
    @SerializedName("pagination")
    val pagination: Pagination = Pagination()
)