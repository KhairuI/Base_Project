package com.example.baseproject.ui.firebase.fragment.image.imageDialog

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.fragment.image.imageDialog.delegate.ImageDialogViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageDialogViewModel @Inject constructor(
    private val imageDialogViewModelDelegate: ImageDialogViewModelDelegate
) : BaseViewModel(), ImageDialogViewModelDelegate by imageDialogViewModelDelegate