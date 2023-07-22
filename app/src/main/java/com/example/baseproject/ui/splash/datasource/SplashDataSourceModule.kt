package com.example.baseproject.ui.splash.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SplashDataSourceModule {

    @Provides
    @Singleton
    internal fun provideSplashDataSource(splashDataSourceImpl: SplashDataSourceImpl): SplashDataSource =
        splashDataSourceImpl
}