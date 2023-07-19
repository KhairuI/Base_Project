package com.example.baseproject.ui.post.datasource

import android.content.Context
import android.util.Log
import com.example.baseproject.R
import com.example.baseproject.data.network.response.Quote
import com.example.baseproject.data.network.service.ApiServicePost
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

interface PostDataSource {
    suspend fun login(): Flow<Result<Quote>>
}

class PostDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiServicePost: ApiServicePost,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : PostDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun login(): Flow<Result<Quote>> = flow {
        try {
            emit(Result.Loading())

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            val response = apiServicePost.postApiCall()

            Log.d("xxx", "body: ${response.body()}")
            Log.d("xxx", "code: ${response.code()}")
            Log.d("xxx", "message: ${response.message()}")
            if (!response.isSuccessful) {
                response.errorBody()?.string().let { errorMsg ->
                    emit(Result.Error(UiText.DynamicString(errorMsg)))
                    return@flow
                }
            }

            response.body()?.let { data ->
                Log.d("xxx", "data: $data")
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