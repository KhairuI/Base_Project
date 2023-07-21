package com.example.baseproject.ui.firebase.fragment.list.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ListDataSourceModule {

    @Provides
    @Singleton
    internal fun provideListDataSource(listDataSourceImpl: ListDataSourceImpl): ListDataSource =
        listDataSourceImpl
}