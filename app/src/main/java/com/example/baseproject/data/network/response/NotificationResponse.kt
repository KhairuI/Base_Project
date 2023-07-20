package com.example.baseproject.data.network.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @Expose @SerializedName("success") var success: Int? = null,
    @Expose @SerializedName("failure") var failure: Int? = null
)