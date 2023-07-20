package com.example.baseproject.di.helper

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.data.network.service.ApiServiceFCM
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

class ApiServiceFCMHelper @Inject constructor(
    private val chuckerInterceptor: ChuckerInterceptor
) {

    operator fun invoke(): ApiServiceFCM {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.FCM_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
        return retrofit.create(ApiServiceFCM::class.java)
    }

    private fun client(): OkHttpClient = with(OkHttpClient().newBuilder()) {
        addInterceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .addHeader(NetworkConstants.AUTHORIZATION, NetworkConstants.AUTHORIZATION_VALUE)
                .addHeader(NetworkConstants.CONTENT_TYPE, NetworkConstants.APPLICATION_JSON).build()
            chain.proceed(modifiedRequest)
        }
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