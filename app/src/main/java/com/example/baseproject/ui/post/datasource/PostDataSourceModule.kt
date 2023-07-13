package com.example.baseproject.ui.post.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PostDataSourceModule {

    @Provides
    @Singleton
    internal fun providePostDataSource(postDataSourceImpl: PostDataSourceImpl): PostDataSource =
        postDataSourceImpl
}