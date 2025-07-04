package com.example.data.di

import android.content.Context
import com.example.data.data.AuthRepoImpl
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.network.auth.api.AuthApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
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
    fun provideAuthApiInstance(@Named("auth_api") retrofit: Retrofit): AuthApiInstance {
        return retrofit.create(AuthApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(
        authManager: AuthManager,
        authApiInstance: AuthApiInstance,
    ): AuthRepo {
        return AuthRepoImpl(authManager, authApiInstance)
    }
}