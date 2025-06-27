package com.example.data.di

import android.content.Context
import com.example.data.data.OnBoardingRepoImpl
import com.example.data.domain.OnBoardingRepo
import com.example.local.datastore.onboarding.OnBoardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingModule {

    @Provides
    @Singleton
    fun provideOnBoardingManager(
        @ApplicationContext context: Context
    ): OnBoardingManager {
        return OnBoardingManager(context)
    }

    @Provides
    @Singleton
    fun provideOnBoardingRepo(
        onBoardingManager: OnBoardingManager
    ): OnBoardingRepo {
        return OnBoardingRepoImpl(onBoardingManager)
    }
}