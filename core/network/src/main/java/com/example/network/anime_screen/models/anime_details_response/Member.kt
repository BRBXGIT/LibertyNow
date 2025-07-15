package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("role")
    val role: Role = Role(),
    @SerializedName("user")
    val user: Any? = null
)