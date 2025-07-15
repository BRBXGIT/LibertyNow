package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("ending")
    val ending: Ending = Ending(),
    @SerializedName("hls_1080")
    val hls1080: String? = null,
    @SerializedName("hls_480")
    val hls480: String = "",
    @SerializedName("hls_720")
    val hls720: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("name_english")
    val nameEnglish: Any? = null,
    @SerializedName("opening")
    val opening: Opening = Opening(),
    @SerializedName("ordinal")
    val ordinal: Int = 0,
    @SerializedName("preview")
    val preview: Preview = Preview(),
    @SerializedName("release_id")
    val releaseId: Int = 0,
    @SerializedName("rutube_id")
    val rutubeId: Any? = null,
    @SerializedName("sort_order")
    val sortOrder: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("youtube_id")
    val youtubeId: Any? = null
)