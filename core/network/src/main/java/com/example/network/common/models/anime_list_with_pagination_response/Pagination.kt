package com.example.network.common.models.anime_list_with_pagination_response


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("current_page")
    val currentPage: Int = 0,
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("per_page")
    val perPage: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0
)