package com.example.data.di

import android.content.Context
import com.example.data.data.AuthRepoImpl
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthManager(
        @ApplicationContext context: Context
    ): AuthManager {
        return AuthManager(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(authManager: AuthManager): AuthRepo {
        return AuthRepoImpl(authManager)
    }
}