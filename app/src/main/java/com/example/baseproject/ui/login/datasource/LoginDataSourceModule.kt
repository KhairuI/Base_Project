package com.example.baseproject.ui.login.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LoginDataSourceModule {

    @Provides
    @Singleton
    internal fun provideLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource =
        loginDataSourceImpl
}