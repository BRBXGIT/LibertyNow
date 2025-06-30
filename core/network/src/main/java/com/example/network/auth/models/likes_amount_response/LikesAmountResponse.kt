package com.example.network.auth.models.likes_amount_response


import com.google.gson.annotations.SerializedName

data class LikesAmountResponse(
    @SerializedName("list")
    val list: List<Item0> = listOf(),
    @SerializedName("pagination")
    val pagination: Pagination = Pagination()
)