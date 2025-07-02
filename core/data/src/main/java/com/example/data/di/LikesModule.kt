package com.example.data.di

import com.example.data.data.LikesRepoImpl
import com.example.data.domain.LikesRepo
import com.example.network.auth.api.LikesApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LikesModule {

    @Provides
    @Singleton
    fun provideLikesApiInstance(@Named("main_api") retrofit: Retrofit): LikesApiInstance {
        return retrofit.create(LikesApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideLikesRepo(likesApiInstance: LikesApiInstance): LikesRepo {
        return LikesRepoImpl(likesApiInstance)
    }
}