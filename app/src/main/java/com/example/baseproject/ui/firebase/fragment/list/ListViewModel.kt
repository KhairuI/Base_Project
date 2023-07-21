package com.example.baseproject.ui.firebase.fragment.list

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.firebase.fragment.list.delegate.ListViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listViewModelDelegate: ListViewModelDelegate
) : BaseViewModel(), ListViewModelDelegate by listViewModelDelegate


