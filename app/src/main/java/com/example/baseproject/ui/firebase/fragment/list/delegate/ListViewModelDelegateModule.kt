package com.example.baseproject.ui.firebase.fragment.list.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideListViewModelDelegate(listViewModelDelegateImpl: ListViewModelDelegateImpl): ListViewModelDelegate =
        listViewModelDelegateImpl
}