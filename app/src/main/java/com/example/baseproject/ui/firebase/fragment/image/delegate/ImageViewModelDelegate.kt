package com.example.baseproject.ui.firebase.fragment.image.delegate

import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.firebase.fragment.image.datasource.ImageDataSource
import com.example.baseproject.ui.firebase.fragment.image.model.ModelImage
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImageViewModelDelegate {
    fun getList()
    val getListResponse: Flow<Result<List<ModelImage>>>
}

internal class ImageViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val imageDataSource: ImageDataSource,
) : ImageViewModelDelegate {

    private val _getListResponse = Channel<Result<List<ModelImage>>>(Channel.CONFLATED)
    override val getListResponse = _getListResponse.receiveAsFlow()

    override fun getList() {
        applicationScope.launch {
            imageDataSource.getList().collect { result ->
                _getListResponse.tryOffer(result)
            }
        }
    }
}