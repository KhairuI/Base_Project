package com.example.baseproject.ui.quote.datasource

import android.content.Context
import com.example.baseproject.R
import com.example.baseproject.data.network.response.Quote
import com.example.baseproject.data.network.service.ApiServiceQuote
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface QuoteDataSource {
    suspend fun getQuote(): Flow<Result<Quote>>
}

class QuoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiServiceQuote: ApiServiceQuote,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : QuoteDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun getQuote(): Flow<Result<Quote>> = flow {
        try {

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }
            emit(Result.Loading())

            val response = apiServiceQuote.quoteApiCall()
            if (!response.isSuccessful) {
                response.errorBody()?.string().let { errorMsg ->
                    emit(Result.Error(UiText.DynamicString(errorMsg)))
                    return@flow
                }
            }

            response.body()?.let { data ->
                emit(Result.Success(data))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)
}