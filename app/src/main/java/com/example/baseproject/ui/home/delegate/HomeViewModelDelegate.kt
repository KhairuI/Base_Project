package com.example.baseproject.ui.home.delegate

import com.example.baseproject.data.database.repository.User
import com.example.baseproject.data.network.response.NotificationResponse
import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.home.datasource.HomeDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface HomeViewModelDelegate {
    fun setUser(user: User)
    fun getAllUser()
    val getAllUserResponse: Flow<Result<List<User>>>
    fun sendNotification()
    val sendNotificationResponse: Flow<Result<NotificationResponse>>
}

internal class HomeViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val homeDataSource: HomeDataSource,
) : HomeViewModelDelegate {

    private val _getAllUserResponse = Channel<Result<List<User>>>(Channel.CONFLATED)
    override val getAllUserResponse = _getAllUserResponse.receiveAsFlow()

    private val _sendNotificationResponse = Channel<Result<NotificationResponse>>(Channel.CONFLATED)
    override val sendNotificationResponse = _sendNotificationResponse.receiveAsFlow()

    override fun setUser(user: User) {
        applicationScope.launch {
            homeDataSource.setUser(user)
        }
    }

    override fun getAllUser() {
        applicationScope.launch {
            homeDataSource.getAllUser().collect { result ->
                _getAllUserResponse.tryOffer(result)
            }
        }
    }

    override fun sendNotification() {
        applicationScope.launch {
            homeDataSource.sendNotification().collect { result ->
                _sendNotificationResponse.tryOffer(result)
            }
        }
    }

}