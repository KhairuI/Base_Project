package com.example.baseproject.ui.firebase.fragment.preview.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PreviewDataSourceModule {

    @Provides
    @Singleton
    internal fun providePreviewDataSource(previewDataSourceImpl: PreviewDataSourceImpl): PreviewDataSource =
        previewDataSourceImpl
}