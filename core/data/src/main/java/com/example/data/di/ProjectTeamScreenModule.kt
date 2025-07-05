package com.example.data.di

import com.example.data.data.ProjectTeamScreenRepoImpl
import com.example.data.domain.ProjectTeamScreenRepo
import com.example.network.project_team_screen.api.ProjectTeamApiInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectTeamScreenModule {

    @Provides
    @Singleton
    fun provideProjectTeamApiInstance(@Named("main_api") retrofit: Retrofit): ProjectTeamApiInstance {
        return retrofit.create(ProjectTeamApiInstance::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectTeamScreenRepo(apiInstance: ProjectTeamApiInstance): ProjectTeamScreenRepo {
        return ProjectTeamScreenRepoImpl(apiInstance)
    }
}