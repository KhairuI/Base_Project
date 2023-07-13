package com.example.baseproject.di.helper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.utils.NetworkConstants

class ChuckerInterceptorHelper(
    private val context: Context
) {

    operator fun invoke(): ChuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(NetworkConstants.CHUCK_MAX_CONTENT_LENGTH)
        .redactHeaders(NetworkConstants.AUTH_TOKEN, NetworkConstants.BEARER)
        .alwaysReadResponseBody(true)
        .build()
}