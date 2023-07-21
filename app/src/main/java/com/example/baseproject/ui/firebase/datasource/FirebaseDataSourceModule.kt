package com.example.baseproject.ui.firebase.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseDataSourceModule {

    @Provides
    @Singleton
    internal fun provideFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource =
        firebaseDataSourceImpl
}