package com.example.baseproject.data.network.request


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotificationModel(
    @Expose @SerializedName("body") internal val body: String? = null,
    @Expose @SerializedName("title") internal val title: String? = null
)