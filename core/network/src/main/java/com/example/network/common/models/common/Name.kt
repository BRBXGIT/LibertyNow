package com.example.network.common.models.common


import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("alternative")
    val alternative: String = "",
    @SerializedName("english")
    val english: String = "",
    @SerializedName("main")
    val main: String = ""
)