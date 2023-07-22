package com.example.baseproject.ui.splash.delegate

import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.splash.datasource.SplashDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SplashViewModelDelegate {
    fun checkUserExist()
    val checkUserExistResponse: Flow<Result<Boolean>>
}

internal class SplashViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val splashDataSource: SplashDataSource,
) : SplashViewModelDelegate {

    private val _checkUserExistResponse = Channel<Result<Boolean>>(Channel.CONFLATED)
    override val checkUserExistResponse = _checkUserExistResponse.receiveAsFlow()

    override fun checkUserExist() {
        applicationScope.launch {
            splashDataSource.checkUserExist().collect { result ->
                _checkUserExistResponse.tryOffer(result)
            }
        }
    }

}