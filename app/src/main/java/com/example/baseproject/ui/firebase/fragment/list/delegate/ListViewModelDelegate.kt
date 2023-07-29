package com.example.baseproject.ui.firebase.fragment.list.delegate

import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.firebase.fragment.list.datasource.ListDataSource
import com.example.baseproject.ui.firebase.fragment.list.model.ModelList
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ListViewModelDelegate{
    fun getList()
    val getListResponse: Flow<Result<List<ModelList>>>
}

internal class ListViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val listDataSource: ListDataSource,
) : ListViewModelDelegate{

    private val _getListResponse = Channel<Result<List<ModelList>>>(Channel.CONFLATED)
    override val getListResponse = _getListResponse.receiveAsFlow()

    override fun getList() {
        applicationScope.launch {
            listDataSource.getList().collect { result ->
                _getListResponse.tryOffer(result)
            }
        }
    }
}