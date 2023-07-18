package com.example.baseproject.utils

import com.example.baseproject.BuildConfig

object AppConstants {

    internal const val BASE_URL: String = BuildConfig.BASE_URL
    internal const val BASE_URL_POST: String = "https://jsonplaceholder.typicode.com/"
    internal const val BASE_URL_LOGIN: String = "https://test-live.datadashbd.com/"
    internal const val IS_BUILD_SHOW: Boolean = BuildConfig.IS_DEVELOPMENT
    internal const val IS_LOG_ENABLE: Boolean = BuildConfig.IS_DEVELOPMENT
    internal const val IS_SECURE_SCREENSHOT: Boolean = false

    internal const val DB_NAME = "base_project.db"
    internal const val DB_VERSION = BuildConfig.VERSION_CODE
    internal const val PREF_STORAGE_NAME = "pref_storage_client"
    internal const val PREF_SHARED_NAME = "pref_shared_client"
    internal const val PREF_NAME = "pref_base_project"
}