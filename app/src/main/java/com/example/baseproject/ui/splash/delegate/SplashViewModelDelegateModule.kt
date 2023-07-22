package com.example.baseproject.ui.splash.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideSplashViewModelDelegate(splashViewModelDelegateImpl: SplashViewModelDelegateImpl): SplashViewModelDelegate =
        splashViewModelDelegateImpl
}