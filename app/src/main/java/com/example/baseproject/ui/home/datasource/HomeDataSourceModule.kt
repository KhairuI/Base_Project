package com.example.baseproject.ui.home.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HomeDataSourceModule {

    @Provides
    @Singleton
    internal fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource =
        homeDataSourceImpl
}