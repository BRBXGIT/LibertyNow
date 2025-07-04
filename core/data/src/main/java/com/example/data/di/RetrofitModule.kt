package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    // AniLibria api often give timeouts
    @Provides
    @Singleton
    @Named("main_okhttp")
    fun provideMainOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("main_api")
    fun provideMainRetrofit(
        gson: GsonConverterFactory,
        @Named("main_okhttp") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.anilibria.tv/v3/")
            .addConverterFactory(gson)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named("auth_okhttp")
    fun provideAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .hostnameVerifier { hostname, _ -> hostname == "www.anilibria.tv" }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("auth_api")
    fun provideAuthRetrofit(
        gson: GsonConverterFactory,
        @Named("auth_okhttp") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.anilibria.tv/")
            .addConverterFactory(gson)
            .client(okHttpClient)
            .build()
    }
}