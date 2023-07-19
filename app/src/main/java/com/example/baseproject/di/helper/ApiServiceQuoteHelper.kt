package com.example.baseproject.di.helper

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.data.network.service.ApiServiceQuote
import com.example.baseproject.utils.AppConstants
import com.example.baseproject.utils.NetworkConstants
import com.example.baseproject.utils.extension.debug
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiServiceQuoteHelper @Inject constructor(
    private val chuckerInterceptor: ChuckerInterceptor
) {

    operator fun invoke(): ApiServiceQuote {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
        return retrofit.create(ApiServiceQuote::class.java)
    }

    private fun client(): OkHttpClient = with(OkHttpClient().newBuilder()) {
        debug {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            addNetworkInterceptor(StethoInterceptor())
            addInterceptor(chuckerInterceptor)
            addInterceptor(httpLoggingInterceptor)
        }
        hostnameVerifier { _, _ -> true }
        retryOnConnectionFailure(false)
        connectTimeout(NetworkConstants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
        readTimeout(NetworkConstants.TIMEOUT_READ, TimeUnit.SECONDS)
        writeTimeout(NetworkConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
        build()
    }
}