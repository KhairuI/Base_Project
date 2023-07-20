package com.example.baseproject.data.network.service

import com.example.baseproject.data.network.ApiEndPoint
import com.example.baseproject.data.network.request.NotificationRequest
import com.example.baseproject.data.network.response.NotificationResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceFCM {

    @POST(ApiEndPoint.ENDPOINT_FCM)
    suspend fun sendNotification(@Body request: NotificationRequest): Response<NotificationResponse>

}