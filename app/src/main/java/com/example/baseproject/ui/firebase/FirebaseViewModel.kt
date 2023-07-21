package com.example.baseproject.ui.firebase

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.delegate.FirebaseViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val firebaseViewModelDelegate: FirebaseViewModelDelegate
) : BaseViewModel(), FirebaseViewModelDelegate by firebaseViewModelDelegate