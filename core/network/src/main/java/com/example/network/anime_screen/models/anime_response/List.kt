package com.example.network.anime_screen.models.anime_response


import com.google.gson.annotations.SerializedName

data class List(
    @SerializedName("1")
    val x1: X1 = X1(),
    @SerializedName("10")
    val x10: X1 = X1(),
    @SerializedName("11")
    val x11: X1 = X1(),
    @SerializedName("12")
    val x12: X1 = X1(),
    @SerializedName("2")
    val x2: X1 = X1(),
    @SerializedName("3")
    val x3: X1 = X1(),
    @SerializedName("4")
    val x4: X1 = X1(),
    @SerializedName("5")
    val x5: X1 = X1(),
    @SerializedName("6")
    val x6: X1 = X1(),
    @SerializedName("7")
    val x7: X1 = X1(),
    @SerializedName("8")
    val x8: X1 = X1(),
    @SerializedName("9")
    val x9: X1 = X1()
)