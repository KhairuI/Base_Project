package com.example.baseproject.ui.splash.datasource

import android.content.Context
import com.example.baseproject.R
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.isNetworkConnected
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface SplashDataSource {
    suspend fun checkUserExist(): Flow<Result<Boolean>>
}

class SplashDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val auth: FirebaseAuth,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : SplashDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun checkUserExist(): Flow<Result<Boolean>> = flow {
        try {

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            val firebaseUser = auth.currentUser
            if (firebaseUser?.uid != null) {
                emit(Result.Success(true))
            } else {
                emit(Result.Success(false))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

}