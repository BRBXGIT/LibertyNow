package com.example.data.di

import com.example.data.data.AnimeScreenRepoImpl
import com.example.data.domain.AnimeScreenRepo
import com.example.network.anime_screen.api.AnimeScreenApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnimeScreenModule {

    @Provides
    @Singleton
    fun provideAnimeScreenApiInstance(retrofit: Retrofit): AnimeScreenApiInstance {
        return retrofit.create(AnimeScreenApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeScreenRepo(apiInstance: AnimeScreenApiInstance): AnimeScreenRepo {
        return AnimeScreenRepoImpl(apiInstance)
    }
}