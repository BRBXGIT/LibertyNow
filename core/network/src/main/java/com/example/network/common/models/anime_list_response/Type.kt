package com.example.network.common.models.anime_list_response


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("value")
    val value: String = ""
)