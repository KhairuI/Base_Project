package com.example.baseproject.ui.home.datasource

import android.content.Context
import com.example.baseproject.data.database.repository.User
import com.example.baseproject.data.database.repository.UserRepo
import com.example.baseproject.data.preferences.PreferenceHelper
import com.example.baseproject.di.ext.IoDispatcher
import com.example.baseproject.ui.base.datasource.NetworkHandlerDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.extension.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface HomeDataSource {
    suspend fun setUser(user: User)
    suspend fun getAllUser(): Flow<Result<List<User>>>
    suspend fun setLogin(isLogin: Boolean)
    fun getLogin(): Boolean
}

class HomeDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val preferenceHelper: PreferenceHelper,
    private val userRepo: UserRepo,
    private val networkHandlerDataSource: NetworkHandlerDataSource
) : HomeDataSource {

    private fun hasInternetConnection(): Boolean = context.isNetworkConnected()

    override suspend fun setUser(user: User) {
        userRepo.insert(user)
    }

    override suspend fun getAllUser(): Flow<Result<List<User>>> = flow {
        try {
            //emit(Result.Loading())

            emit(Result.Success(userRepo.load()))

        } catch (e: Exception) {
            emit(Result.Error(networkHandlerDataSource.handleException(e)))
        } finally {
            emit(Result.Loading(false))
        }
    }.flowOn(ioDispatcher)

    override suspend fun setLogin(isLogin: Boolean) {
        preferenceHelper.setLoggedIn(isLogin)
    }

    override fun getLogin(): Boolean = preferenceHelper.isLoggedIn()
}