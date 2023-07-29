package com.example.baseproject.ui.firebase.fragment.image.imageDialog.delegate

import android.net.Uri
import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.firebase.fragment.image.imageDialog.datasource.ImageDialogDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.data
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ImageDialogViewModelDelegate {
    fun image(name: String, uri: Uri)
    val imageResponse: Flow<Result<String>>
    fun saveUrl(name: String, url: String)
    val saveUrlResponse: Flow<Result<String>>
}

internal class ImageDialogViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val imageDialogDataSource: ImageDialogDataSource,
) : ImageDialogViewModelDelegate {

    private val _imageResponse = Channel<Result<String>>(Channel.CONFLATED)
    override val imageResponse = _imageResponse.receiveAsFlow()

    private val _saveUrlResponse = Channel<Result<String>>(Channel.CONFLATED)
    override val saveUrlResponse = _saveUrlResponse.receiveAsFlow()


    override fun image(name: String, uri: Uri) {
        applicationScope.launch {
            imageDialogDataSource.image(name, uri).collect { result ->
                _imageResponse.tryOffer(result)
            }
        }
    }

    override fun saveUrl(name: String, url: String) {
        applicationScope.launch {
            imageDialogDataSource.saveUrl(name, url).collect { result ->
                _saveUrlResponse.tryOffer(result)
            }
        }
    }
}