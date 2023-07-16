package com.example.baseproject.ui.base.datasource

import android.util.Log
import com.example.baseproject.R
import com.example.baseproject.data.network.response.ApiError
import com.example.baseproject.utils.arch.UiText
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

interface NetworkHandlerDataSource {

    suspend fun handleException(e: Exception): UiText
}

class NetworkHandlerDataSourceImpl @Inject constructor() : NetworkHandlerDataSource {

    override suspend fun handleException(e: Exception): UiText {
        Log.d("xxx", "Exception: ${e.message}")
        e.cause?.let { throwable ->
            Log.d("xxx", "handleException: $throwable")
            firebaseNotify(userId = 0, throwable = throwable)
        }
        val uiText: UiText = when (e) {
            is HttpException -> {
                when (e.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        UiText.StringResource(R.string.http_unauthorized)
                    }

                    HttpsURLConnection.HTTP_FORBIDDEN -> UiText.StringResource(R.string.http_forbidden)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR -> UiText.StringResource(R.string.http_internal_error)
                    HttpsURLConnection.HTTP_BAD_REQUEST -> UiText.StringResource(R.string.http_bad_request)
                    0 -> UiText.StringResource(R.string.http_no_internet)
                    else -> defaultError()
                }
            }

            is ApiError -> e.errorMsg?.let { error -> UiText.DynamicString(error) }
                ?: defaultError()

            is JsonSyntaxException -> UiText.StringResource(R.string.http_json_exception)

            is SocketTimeoutException -> UiText.StringResource(R.string.http_socket_timeout)

            is UnknownHostException -> UiText.StringResource(R.string.http_unknown_host)

            else -> defaultError()
        }
        return uiText
    }

    private fun defaultError() = UiText.StringResource(R.string.api_default_error)

    private fun firebaseNotify(userId: Int?, throwable: Throwable) {
        FirebaseCrashlytics.getInstance().apply {
            userId?.let { id -> setUserId(id.toString()) }
            when (throwable) {
                is HttpException -> try {
                    throwable.response()?.raw()?.request?.body?.let { body ->
                        setCustomKey("request_body", body.toString())
                    }
                    throwable.response()?.raw()?.request?.headers?.let { headers ->
                        setCustomKey("request_headers", headers.toString())
                    }
                    throwable.response()?.raw()?.request?.url?.let { url ->
                        setCustomKey("request_url", url.toString())
                    }
                    setCustomKey("error_code", throwable.code())
                    throwable.response()?.errorBody()?.let { errorBody ->
                        setCustomKey("error_body", errorBody.string())
                    }
                    throwable.message?.let { message -> setCustomKey("message", message) }
                    throwable.localizedMessage?.let { localizedMessage ->
                        setCustomKey("localized_message", localizedMessage)
                    }
                } catch (e: Exception) {
                    e.message?.let { error -> setCustomKey("error", error) }
                    e.printStackTrace()
                }

                is ApiError -> throwable.errorMsg?.let { error -> setCustomKey("error", error) }
                is JsonSyntaxException -> setCustomKey("error", "JsonSyntaxException")
                is SocketTimeoutException -> setCustomKey("error", "SocketTimeoutException")
                is UnknownHostException -> setCustomKey("error", "UnknownHostException")
                is SocketException -> setCustomKey("error", "SocketException")
                else -> setCustomKey("error", "Unknown")
            }
            recordException(throwable)
        }
    }
}