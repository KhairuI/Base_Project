package com.example.baseproject.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiError : RuntimeException() {
    @Expose
    @SerializedName("error")
    private var error: Int? = null

    @Expose
    @SerializedName("statusCode")
    var statusCode: Int? = null

    @Expose
    @SerializedName("errorMsg")
    var errorMsg: String? = null
}

