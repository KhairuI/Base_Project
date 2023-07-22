package com.example.baseproject.ui.login.datasource

import android.content.Context
import com.example.baseproject.R
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.UiText
import com.example.baseproject.utils.extension.isNetworkConnected
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginDataSource {
    fun login(authCredential: AuthCredential): Flow<Result<Boolean>>
}

class LoginDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val auth: FirebaseAuth,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : LoginDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    @OptIn(DelicateCoroutinesApi::class)
    override fun login(authCredential: AuthCredential): Flow<Result<Boolean>> = flow {
        try {

            emit(Result.Loading())

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    GlobalScope.launch(Dispatchers.Default) {

                    }
                   // emit(Result.Success(true))


                } else {
                    GlobalScope.launch(Dispatchers.Default) {
                        emit(Result.Error(UiText.DynamicString(task.exception.toString())))
                    }
                }

            }.addOnFailureListener { e ->
                GlobalScope.launch(Dispatchers.IO) {
                    emit(Result.Error(UiText.DynamicString(e.toString())))
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

}