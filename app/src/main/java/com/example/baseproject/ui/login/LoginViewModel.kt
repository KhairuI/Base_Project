package com.example.baseproject.ui.login

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.login.delegate.LoginViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginViewModelDelegate: LoginViewModelDelegate
) : BaseViewModel(), LoginViewModelDelegate by loginViewModelDelegate