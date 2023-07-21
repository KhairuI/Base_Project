package com.example.baseproject.ui.firebase.fragment.image.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImageDataSourceModule {

    @Provides
    @Singleton
    internal fun provideImageDataSource(imageDataSourceImpl: ImageDataSourceImpl): ImageDataSource =
        imageDataSourceImpl
}