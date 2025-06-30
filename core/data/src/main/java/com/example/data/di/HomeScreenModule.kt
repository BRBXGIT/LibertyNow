package com.example.data.di

import com.example.data.data.HomeScreenRepoImpl
import com.example.data.domain.HomeScreenRepo
import com.example.network.home_screen.api.HomeScreenApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeScreenModule {

    @Provides
    @Singleton
    fun provideHomeScreenApiInstance(@Named("main_api") retrofit: Retrofit): HomeScreenApiInstance {
        return retrofit.create(HomeScreenApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeScreenRepo(apiInstance: HomeScreenApiInstance): HomeScreenRepo {
        return HomeScreenRepoImpl(apiInstance)
    }
}