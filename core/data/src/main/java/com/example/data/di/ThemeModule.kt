package com.example.data.di

import android.content.Context
import com.example.data.data.ThemeRepoImpl
import com.example.data.domain.ThemeRepo
import com.example.local.datastore.app_theme.AppThemeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {

    @Provides
    @Singleton
    fun provideAppThemeManager(
        @ApplicationContext context: Context
    ): AppThemeManager {
        return AppThemeManager(context)
    }

    @Provides
    @Singleton
    fun provideThemeRepo(appThemeManager: AppThemeManager): ThemeRepo {
        return ThemeRepoImpl(appThemeManager)
    }
}