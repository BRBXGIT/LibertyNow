package com.example.data.di

import com.example.data.data.SearchScreenRepoImpl
import com.example.data.domain.SearchScreenRepo
import com.example.network.search_screen.api.SearchScreenApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchScreenModule {

    @Provides
    @Singleton
    fun provideSearchScreenApiInstance(retrofit: Retrofit): SearchScreenApiInstance {
        return retrofit.create(SearchScreenApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchScreenRepo(apiInstance: SearchScreenApiInstance): SearchScreenRepo {
        return SearchScreenRepoImpl(apiInstance)
    }
}