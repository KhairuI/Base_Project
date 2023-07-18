package com.example.baseproject.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose @SerializedName("email") internal val email: String? = null,
    @Expose @SerializedName("password") internal val password: String? = null
)
