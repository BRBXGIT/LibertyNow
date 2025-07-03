package com.example.data.domain

import kotlinx.coroutines.flow.Flow

interface PlayerFeaturesRepo {

    val videoQuality: Flow<Int?>

    val showSkipOpeningButton: Flow<Boolean?>

    val autoPlay: Flow<Boolean?>

    suspend fun saveVideoQuality(quality: Int)

    suspend fun saveShowSkipOpeningButton(show: Boolean)

    suspend fun saveAutoplay(autoplay: Boolean)
}