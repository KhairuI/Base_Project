package com.example.baseproject.ui.home

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.home.delegate.HomeViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeViewModelDelegate: HomeViewModelDelegate
) : BaseViewModel(), HomeViewModelDelegate by homeViewModelDelegate