package com.example.network.anime_screen.models.anime_details_response


import com.google.gson.annotations.SerializedName

data class Release(
    @SerializedName("added_in_abandoned_collection")
    val addedInAbandonedCollection: Int = 0,
    @SerializedName("added_in_planned_collection")
    val addedInPlannedCollection: Int = 0,
    @SerializedName("added_in_postponed_collection")
    val addedInPostponedCollection: Int = 0,
    @SerializedName("added_in_users_favorites")
    val addedInUsersFavorites: Int = 0,
    @SerializedName("added_in_watched_collection")
    val addedInWatchedCollection: Int = 0,
    @SerializedName("added_in_watching_collection")
    val addedInWatchingCollection: Int = 0,
    @SerializedName("age_rating")
    val ageRating: AgeRating = AgeRating(),
    @SerializedName("alias")
    val alias: String = "",
    @SerializedName("average_duration_of_episode")
    val averageDurationOfEpisode: Int = 0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("episodes_total")
    val episodesTotal: Int = 0,
    @SerializedName("external_player")
    val externalPlayer: String = "",
    @SerializedName("fresh_at")
    val freshAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_blocked_by_copyrights")
    val isBlockedByCopyrights: Boolean = false,
    @SerializedName("is_blocked_by_geo")
    val isBlockedByGeo: Boolean = false,
    @SerializedName("is_in_production")
    val isInProduction: Boolean = false,
    @SerializedName("is_ongoing")
    val isOngoing: Boolean = false,
    @SerializedName("name")
    val name: Name = Name(),
    @SerializedName("notification")
    val notification: Any? = Any(),
    @SerializedName("poster")
    val poster: Poster = Poster(),
    @SerializedName("publish_day")
    val publishDay: PublishDay = PublishDay(),
    @SerializedName("season")
    val season: Season = Season(),
    @SerializedName("type")
    val type: TypeXX = TypeXX(),
    @SerializedName("updated_at")
    val updatedAt: String = "",
    @SerializedName("year")
    val year: Int = 0
)