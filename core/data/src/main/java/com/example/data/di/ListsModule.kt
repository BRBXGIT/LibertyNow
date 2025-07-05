package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.data.ListsRepoImpl
import com.example.data.domain.ListsRepo
import com.example.local.db.lists_db.ListsAnimeDao
import com.example.local.db.lists_db.ListsDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListsModule {

    @Provides
    @Singleton
    fun provideListsDao(
        @ApplicationContext context: Context
    ): ListsAnimeDao {
        return Room.databaseBuilder(
            context = context,
            klass = ListsDb::class.java,
            name = "lists_db"
        ).build().listsAnimeDao()
    }

    @Provides
    @Singleton
    fun provideListsRepo(listsAnimeDao: ListsAnimeDao): ListsRepo {
        return ListsRepoImpl(listsAnimeDao)
    }
}