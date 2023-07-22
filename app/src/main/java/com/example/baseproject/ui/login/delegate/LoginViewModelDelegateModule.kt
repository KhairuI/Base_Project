package com.example.baseproject.ui.login.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideLoginViewModelDelegate(loginViewModelDelegateImpl: LoginViewModelDelegateImpl): LoginViewModelDelegate =
        loginViewModelDelegateImpl
}