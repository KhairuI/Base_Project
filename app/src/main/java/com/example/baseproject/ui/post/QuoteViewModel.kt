package com.example.baseproject.ui.post

import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.ui.post.delegate.QuoteViewModelDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteViewModelDelegate: QuoteViewModelDelegate
) : BaseViewModel(), QuoteViewModelDelegate by quoteViewModelDelegate{

    init {
        quoteViewModelDelegate.getQuote()
    }
}