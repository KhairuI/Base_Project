package com.example.baseproject.ui.quote.delegate

import com.example.baseproject.data.network.response.Quote
import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.quote.datasource.QuoteDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface QuoteViewModelDelegate {
    fun getQuote()
    val getQuoteResponse: Flow<Result<Quote>>
}

internal class QuoteViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val quoteDataSource: QuoteDataSource,
) : QuoteViewModelDelegate {

    private val _getQuoteResponse = Channel<Result<Quote>>(Channel.CONFLATED)
    override val getQuoteResponse = _getQuoteResponse.receiveAsFlow()

    override fun getQuote() {
        applicationScope.launch {
            quoteDataSource.getQuote().collect { result ->
                _getQuoteResponse.tryOffer(result)
            }
        }
    }
}