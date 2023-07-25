package com.example.baseproject.ui.firebase.fragment.list.insertDialog.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InsertDialogDataSourceModule {

    @Provides
    @Singleton
    internal fun provideInsertDialogDataSource(insertDialogDataSourceImpl: InsertDialogDataSourceImpl): InsertDialogDataSource =
        insertDialogDataSourceImpl
}