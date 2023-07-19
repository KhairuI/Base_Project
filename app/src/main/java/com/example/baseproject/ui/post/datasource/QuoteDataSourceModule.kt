package com.example.baseproject.ui.post.datasource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object QuoteDataSourceModule {

    @Provides
    @Singleton
    internal fun provideQuoteDataSource(quoteDataSourceImpl: QuoteDataSourceImpl): QuoteDataSource =
        quoteDataSourceImpl
}