package com.example.baseproject.di.ext

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ApiServicePostQualifier

object Qualifier {

    internal const val postApiService: String = "post_api_service"
    internal const val productApiService: String = "product_api_service"

    internal const val appKey: String = "app_key"
    internal const val packageName: String = "package"
    internal const val versionName: String = "version"
    internal const val language: String = "language"
    internal const val deviceId: String = "device_id"
    internal const val prefName: String = "pref_name"

}