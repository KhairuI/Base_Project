package com.example.baseproject.ui.post

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.post.delegate.PostViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postViewModelDelegate: PostViewModelDelegate
) : BaseViewModel(), PostViewModelDelegate by postViewModelDelegate