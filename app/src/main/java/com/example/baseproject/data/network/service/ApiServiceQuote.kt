package com.example.baseproject.data.network.service

import com.example.baseproject.data.network.ApiEndPoint
import com.example.baseproject.data.network.response.Quote
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceQuote {

    @GET(ApiEndPoint.ENDPOINT_QUOTES)
    suspend fun quoteApiCall(): Response<Quote>

}