package com.example.baseproject.ui.firebase.fragment.image

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.fragment.image.delegate.ImageViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val imageViewModelDelegate: ImageViewModelDelegate
) : BaseViewModel(), ImageViewModelDelegate by imageViewModelDelegate


