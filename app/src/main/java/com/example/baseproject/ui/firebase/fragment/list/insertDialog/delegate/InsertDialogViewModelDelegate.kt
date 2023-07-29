package com.example.baseproject.ui.firebase.fragment.list.insertDialog.delegate

import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.firebase.fragment.list.insertDialog.datasource.InsertDialogDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface InsertDialogViewModelDelegate {
    fun insert(text: String)
    val insertResponse: Flow<Result<String>>
}

internal class InsertDialogViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val insertDialogDataSource: InsertDialogDataSource,
) : InsertDialogViewModelDelegate {

    private val _insertResponse = Channel<Result<String>>(Channel.CONFLATED)
    override val insertResponse = _insertResponse.receiveAsFlow()

    override fun insert(text: String) {
        applicationScope.launch {
            insertDialogDataSource.insert(text).collect { result ->
                _insertResponse.tryOffer(result)
            }
        }
    }
}