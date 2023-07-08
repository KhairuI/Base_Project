package com.example.baseproject.ui.setting.select_language

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.setting.select_language.delegate.SelectLanguageDialogViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectLanguageDialogViewModel @Inject constructor(
    private val selectLanguageDialogViewModelDelegate: SelectLanguageDialogViewModelDelegate
) : BaseViewModel(), SelectLanguageDialogViewModelDelegate by selectLanguageDialogViewModelDelegate