package com.example.baseproject.ui.splash

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.splash.delegate.SplashViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashViewModelDelegate: SplashViewModelDelegate
) : BaseViewModel(), SplashViewModelDelegate by splashViewModelDelegate {

    init {
        splashViewModelDelegate.checkUserExist()
    }
}