package com.example.network.auth.models.likes_amount_response


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int = 0,
    @SerializedName("items_per_page")
    val itemsPerPage: Int = 0,
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("total_items")
    val totalItems: Int = 0
)