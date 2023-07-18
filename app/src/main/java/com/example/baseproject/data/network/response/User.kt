package com.example.baseproject.data.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose @SerializedName("id")
    var id: Int? = null,

    @Expose @SerializedName("name")
    var name: String? = null,

    @Expose @SerializedName("phone")
    var phone: String? = null,

    @Expose @SerializedName("email")
    var email: String? = null,

    @Expose @SerializedName("email_verified_at")
    var email_verified_at: String? = null,

    @Expose @SerializedName("user_type_id")
    var user_type_id: Int? = null,

    @Expose @SerializedName("active")
    var active: Int? = null,

    @Expose @SerializedName("created_at")
    var created_at: String? = null,

    @Expose @SerializedName("updated_at")
    var updated_at: String? = null,

    @Expose @SerializedName("client_id")
    var client_id: Int? = null,

    @Expose @SerializedName("api_token")
    var api_token: String? = null,

    @Expose @SerializedName("token_type")
    var token_type: String? = null
)