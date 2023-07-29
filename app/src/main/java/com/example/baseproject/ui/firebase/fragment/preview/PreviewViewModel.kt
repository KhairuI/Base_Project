package com.example.baseproject.ui.firebase.fragment.preview

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.fragment.preview.delegate.PreviewViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val previewViewModelDelegate: PreviewViewModelDelegate
) : BaseViewModel(), PreviewViewModelDelegate by previewViewModelDelegate


