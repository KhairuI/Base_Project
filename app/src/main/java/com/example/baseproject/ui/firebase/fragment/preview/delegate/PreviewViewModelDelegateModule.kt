package com.example.baseproject.ui.firebase.fragment.preview.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreviewViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun providePreviewViewModelDelegate(previewViewModelDelegateImpl: PreviewViewModelDelegateImpl): PreviewViewModelDelegate =
        previewViewModelDelegateImpl
}