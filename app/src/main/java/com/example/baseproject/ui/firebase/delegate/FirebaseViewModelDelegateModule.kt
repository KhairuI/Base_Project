package com.example.baseproject.ui.firebase.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideFirebaseViewModelDelegate(firebaseViewModelDelegateImpl: FirebaseViewModelDelegateImpl): FirebaseViewModelDelegate =
        firebaseViewModelDelegateImpl
}