package com.example.data.di

import android.content.Context
import com.example.data.data.PlayerFeaturesRepoImpl
import com.example.data.domain.PlayerFeaturesRepo
import com.example.local.datastore.player_features.PlayerFeaturesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerFeaturesModule {

    @Provides
    @Singleton
    fun providePlayerFeaturesManager(
        @ApplicationContext context: Context
    ): PlayerFeaturesManager {
        return PlayerFeaturesManager(context)
    }

    @Provides
    @Singleton
    fun providePlayerFeaturesRepo(
        playerFeaturesManager: PlayerFeaturesManager
    ): PlayerFeaturesRepo {
        return PlayerFeaturesRepoImpl(playerFeaturesManager)
    }
}