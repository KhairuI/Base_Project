package com.example.baseproject.data.network.request


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotificationRequest(
    @Expose @SerializedName("to") internal val to: String? = null,
    @Expose @SerializedName("notification") internal val notification: NotificationModel? = null
)