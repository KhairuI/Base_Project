package com.example.baseproject.ui.home.datasource

import android.content.Context
import com.example.baseproject.R
import com.example.baseproject.data.database.repository.User
import com.example.baseproject.data.database.repository.UserRepo
import com.example.baseproject.data.network.request.NotificationModel
import com.example.baseproject.data.network.request.NotificationRequest
import com.example.baseproject.data.network.response.NotificationResponse
import com.example.baseproject.data.network.service.ApiServiceFCM
import com.example.baseproject.data.preferences.PreferenceHelper
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

interface HomeDataSource {
    suspend fun setUser(user: User)
    suspend fun getAllUser(): Flow<Result<List<User>>>
    suspend fun sendNotification(): Flow<Result<NotificationResponse>>
}

class HomeDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val preferenceHelper: PreferenceHelper,
    private val apiServiceFCM: ApiServiceFCM,
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

    override suspend fun sendNotification(): Flow<Result<NotificationResponse>> = flow {
        try {
            // emit(Result.Loading())

            if (!hasInternetConnection()) {
                emit(Result.Error(UiText.StringResource(R.string.http_no_internet)))
                return@flow
            }

            val request = NotificationRequest(
                to = preferenceHelper.getDeviceFcm(),
                notification = NotificationModel(body = "Test Body", title = "Test Title")
            )

            val response = apiServiceFCM.sendNotification(request)
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