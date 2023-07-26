package com.example.baseproject.ui.firebase.fragment.list.insertDialog.delegate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val insertResponse: LiveData<Boolean>
}

internal class InsertDialogViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val insertDialogDataSource: InsertDialogDataSource,
) : InsertDialogViewModelDelegate {

    private val _insertResponse = MutableLiveData<Boolean>()
    override val insertResponse: LiveData<Boolean> get() = _insertResponse

    /*private val _insertResponse = Channel<Result<String>>(Channel.CONFLATED)
    override val insertResponse = _insertResponse.receiveAsFlow()*/

    override fun insert(text: String) {
        _insertResponse.value = insertDialogDataSource.insert(text).value
       /*// _insertResponse.value = Result.Loading()
        _insertResponse.value = insertDialogDataSource.insert(text).value*/
         /* applicationScope.launch {
              insertDialogDataSource.insert2(text).collect { result ->
                  _insertResponse.tryOffer(result)
              }
          }*/
    }
}