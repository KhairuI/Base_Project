package com.example.baseproject.ui.home.delegate

import com.example.baseproject.data.database.repository.User
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
    fun setLogin(isLogin: Boolean)
    fun getLogin(): Boolean
}

internal class HomeViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val homeDataSource: HomeDataSource,
) : HomeViewModelDelegate {

    private val _getAllUserResponse = Channel<Result<List<User>>>(Channel.CONFLATED)
    override val getAllUserResponse = _getAllUserResponse.receiveAsFlow()

    override fun setLogin(isLogin: Boolean) {
        applicationScope.launch {
            homeDataSource.setLogin(isLogin)
        }
    }

    override fun getLogin(): Boolean = homeDataSource.getLogin()

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

}