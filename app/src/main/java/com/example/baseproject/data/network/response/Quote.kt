package com.example.baseproject.data.network.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Quote(
    @Expose @SerializedName("quotes") var quotes: List<QuoteX> = emptyList(),
    @Expose @SerializedName("total") var total: Int? = null,
    @Expose @SerializedName("skip") var skip: Int? = null,
    @Expose @SerializedName("limit") var limit: Int? = null
)