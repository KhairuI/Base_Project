package com.example.baseproject.data.network.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuoteX(
    @Expose @SerializedName("author") var author: String?,
    @Expose @SerializedName("id") var id: Int?,
    @Expose @SerializedName("quote") var quote: String?
)