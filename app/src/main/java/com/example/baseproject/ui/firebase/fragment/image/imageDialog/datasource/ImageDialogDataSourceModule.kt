package com.example.baseproject.ui.firebase.fragment.image.imageDialog.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ImageDialogDataSourceModule {

    @Provides
    @Singleton
    internal fun provideImageDialogDataSource(imageDialogDataSourceImpl: ImageDialogDataSourceImpl): ImageDialogDataSource =
        imageDialogDataSourceImpl
}