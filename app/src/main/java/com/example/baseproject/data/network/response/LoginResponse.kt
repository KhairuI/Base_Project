package com.example.baseproject.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose @SerializedName("data") var `data`: Data? = null,
    @Expose @SerializedName("error") var error: Int? = null,
    @Expose @SerializedName("errorMsg") var errorMsg: String? = null,
    @Expose @SerializedName("statusCode") var statusCode: Int? = null
) {
    data class Data(
        @Expose @SerializedName("users") var users: User? = null,
    )
}
