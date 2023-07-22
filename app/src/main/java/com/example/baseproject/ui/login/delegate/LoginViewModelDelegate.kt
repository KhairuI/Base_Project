package com.example.baseproject.ui.login.delegate

import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.login.datasource.LoginDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginViewModelDelegate {
    fun login(authCredential: AuthCredential)
    val loginResponse: Flow<Result<Boolean>>
}

internal class LoginViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val loginDataSource: LoginDataSource,
) : LoginViewModelDelegate {

    private val _loginResponse = Channel<Result<Boolean>>(Channel.CONFLATED)
    override val loginResponse = _loginResponse.receiveAsFlow()

    override fun login(authCredential: AuthCredential) {
        applicationScope.launch {
            loginDataSource.login(authCredential).collect { result ->
                _loginResponse.tryOffer(result)
            }
        }
    }

}