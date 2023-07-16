package com.example.baseproject.ui.post.delegate

import com.example.baseproject.data.network.response.Posts
import com.example.baseproject.di.ext.ApplicationScope
import com.example.baseproject.ui.post.datasource.PostDataSource
import com.example.baseproject.utils.arch.Result
import com.example.baseproject.utils.arch.tryOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PostViewModelDelegate {
    fun getAllPost()
    val getAllPostResponse: Flow<Result<Posts>>
}

internal class PostViewModelDelegateImpl @Inject constructor(
    @ApplicationScope val applicationScope: CoroutineScope,
    private val postDataSource: PostDataSource,
) : PostViewModelDelegate {

    private val _getAllPostResponse = Channel<Result<Posts>>(Channel.CONFLATED)
    override val getAllPostResponse = _getAllPostResponse.receiveAsFlow()

    override fun getAllPost() {
        applicationScope.launch {
            postDataSource.getAllPost().collect { result ->
                _getAllPostResponse.tryOffer(result)
            }
        }
    }

}