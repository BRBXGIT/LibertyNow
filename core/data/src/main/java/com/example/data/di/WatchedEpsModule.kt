package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.data.WatchedEpsRepoImpl
import com.example.data.domain.WatchedEpsRepo
import com.example.local.db.watched_eps_db.WatchedEpsDao
import com.example.local.db.watched_eps_db.WatchedEpsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WatchedEpsModule {

    @Provides
    @Singleton
    fun provideWatchedEpsDao(
        @ApplicationContext context: Context
    ): WatchedEpsDao {
        return Room.databaseBuilder(
            context = context,
            klass = WatchedEpsDb::class.java,
            name = "watched_eps_db"
        ).build().watchedEpsDao()
    }

    @Provides
    @Singleton
    fun provideWatchedEpsRepo(watchedEpsDao: WatchedEpsDao): WatchedEpsRepo {
        return WatchedEpsRepoImpl(watchedEpsDao)
    }
}