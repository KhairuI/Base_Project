package com.example.baseproject.data.network.service

import com.example.baseproject.data.network.ApiEndPoint
import com.example.baseproject.data.network.response.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceProduct {

    @GET(ApiEndPoint.ENDPOINT_POSTS)
    suspend fun divisionGetApiCall(): Response<PostResponse>



}