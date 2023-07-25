package com.example.baseproject.ui.firebase.fragment.list.insertDialog.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InsertDialogViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideInsertDialogViewModelDelegate(insertDialogViewModelDelegateImpl: InsertDialogViewModelDelegateImpl
    ): InsertDialogViewModelDelegate =
        insertDialogViewModelDelegateImpl
}