package com.example.baseproject.ui.setting

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.setting.delegate.SettingViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingViewModelDelegate: SettingViewModelDelegate
) : BaseViewModel(), SettingViewModelDelegate by settingViewModelDelegate