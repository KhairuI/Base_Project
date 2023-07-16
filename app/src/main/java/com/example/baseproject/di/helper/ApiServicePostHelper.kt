package com.example.baseproject.di.helper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.baseproject.data.network.service.ApiService
import com.example.baseproject.utils.AppConstants
import com.example.baseproject.utils.NetworkConstants
import com.example.baseproject.utils.extension.debug
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiServicePostHelper @Inject constructor(
    private val context: Context,
    private val chuckerInterceptor: ChuckerInterceptor,
    private val header: HeaderModel
) {

    private val dateFormat by lazy { "yyyy-MM-dd'T'HH:mm:ss.SSS" }

    operator fun invoke(): ApiService {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls()
            .setLenient().setDateFormat(dateFormat).create()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL_POST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun client(): OkHttpClient = with(OkHttpClient().newBuilder()) {
        /*addInterceptor { chain ->
            val token = context.accessToken()
            Log.d("LIMO", "token: $token")

            val modifiedRequest = chain.request().newBuilder()
                .addHeader(NetworkConstants.AUTHORIZATION, "${NetworkConstants.BEARER} $token")
                .addHeader(NetworkConstants.ACCEPT, NetworkConstants.APPLICATION_JSON).build()
            chain.proceed(modifiedRequest)
        }
        addNetworkInterceptor { chain ->
            val req =
                chain.request().newBuilder().header(NetworkConstants.APP_KEY, header.appKey)
                    .header(NetworkConstants.PACKAGE, header.packageName)
                    .header(NetworkConstants.VERSION, header.versionName)
                    .header(NetworkConstants.LANGUAGE, header.language)
                    .header(NetworkConstants.DEVICE_ID, header.deviceId)
                    .header(NetworkConstants.CONNECTION, NetworkConstants.CLOSE)
                    .build()
            chain.proceed(req)
        }*/
       /* addNetworkInterceptor { chain ->
            val req = chain.request().newBuilder()
                .header(NetworkConstants.USER_AGENT, NetworkConstants.ANDROID).build()
            chain.proceed(req)
        }*/
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