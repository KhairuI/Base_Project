package com.example.baseproject.ui.firebase.fragment.image.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideImageViewModelDelegate(imageViewModelDelegateImpl: ImageViewModelDelegateImpl): ImageViewModelDelegate =
        imageViewModelDelegateImpl
}