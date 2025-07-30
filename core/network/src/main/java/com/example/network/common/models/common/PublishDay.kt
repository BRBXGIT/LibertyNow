package com.example.network.common.models.common


import com.google.gson.annotations.SerializedName

data class PublishDay(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("value")
    val value: Int = 0
)