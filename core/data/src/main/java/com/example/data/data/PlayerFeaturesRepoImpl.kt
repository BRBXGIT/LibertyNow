package com.example.data.data

import com.example.data.domain.PlayerFeaturesRepo
import com.example.local.datastore.player_features.PlayerFeaturesManager
import javax.inject.Inject

class PlayerFeaturesRepoImpl @Inject constructor(
    private val playerFeaturesManager: PlayerFeaturesManager
): PlayerFeaturesRepo {

    override val autoPlay = playerFeaturesManager.autoPlay

    override val videoQuality = playerFeaturesManager.videoQualityFlow

    override val showSkipOpeningButton = playerFeaturesManager.showSkipOpeningButtonFlow

    override val isCropped = playerFeaturesManager.isCropped

    override suspend fun saveAutoplay(autoplay: Boolean) {
        playerFeaturesManager.saveAutoplay(autoplay)
    }

    override suspend fun saveVideoQuality(quality: Int) {
        playerFeaturesManager.saveVideoQuality(quality)
    }

    override suspend fun saveShowSkipOpeningButton(show: Boolean) {
        playerFeaturesManager.saveShowOpeningButton(show)
    }

    override suspend fun saveIsCropped(isCropped: Boolean) {
        playerFeaturesManager.saveIsCropped(isCropped)
    }
}