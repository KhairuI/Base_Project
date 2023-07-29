package com.example.baseproject.ui.firebase.fragment.image.imageDialog.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageDialogViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideImageDialogViewModelDelegate(imageDialogViewModelDelegateImpl: ImageDialogViewModelDelegateImpl
    ): ImageDialogViewModelDelegate =
        imageDialogViewModelDelegateImpl
}