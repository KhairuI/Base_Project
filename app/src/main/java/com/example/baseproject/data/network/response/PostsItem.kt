package com.example.baseproject.data.network.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostsItem(
    @Expose @SerializedName("body") var body: String? = null,
    @Expose @SerializedName("id") var id: Int? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("userId") val userId: Int? = null
)