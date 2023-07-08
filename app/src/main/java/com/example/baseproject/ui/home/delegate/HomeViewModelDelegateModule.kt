package com.example.baseproject.ui.home.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideHomeViewModelDelegate(homeViewModelDelegateImpl: HomeViewModelDelegateImpl): HomeViewModelDelegate =
        homeViewModelDelegateImpl
}