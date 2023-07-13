package com.example.baseproject.data.network.service

import com.example.baseproject.data.network.ApiEndPoint
import com.example.baseproject.data.network.response.PostResponse
import com.example.baseproject.data.network.response.Posts
import retrofit2.Response
import retrofit2.http.*

interface ApiServicePost {

    @GET(ApiEndPoint.ENDPOINT_POSTS)
    suspend fun postApiCall(): Response<Posts>



}