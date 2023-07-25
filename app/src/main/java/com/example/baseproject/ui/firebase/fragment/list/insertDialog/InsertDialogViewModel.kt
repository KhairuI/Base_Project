package com.example.baseproject.ui.firebase.fragment.list.insertDialog

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.fragment.list.insertDialog.delegate.InsertDialogViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InsertDialogViewModel @Inject constructor(
    private val insertDialogViewModelDelegate: InsertDialogViewModelDelegate
) : BaseViewModel(), InsertDialogViewModelDelegate by insertDialogViewModelDelegate