package com.example.baseproject.ui.quote.delegate

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuoteViewModelDelegateModule {

    @Provides
    @Singleton
    internal fun provideQuoteViewModelDelegate(quoteViewModelDelegateImpl: QuoteViewModelDelegateImpl): QuoteViewModelDelegate =
        quoteViewModelDelegateImpl
}