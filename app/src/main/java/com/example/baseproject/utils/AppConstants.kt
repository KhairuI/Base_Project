package com.example.baseproject.utils

import com.example.baseproject.BuildConfig

object AppConstants {

    internal const val BASE_URL: String = BuildConfig.BASE_URL
    internal const val FCM_URL: String = "https://fcm.googleapis.com/"
    internal const val IS_BUILD_SHOW: Boolean = BuildConfig.IS_DEVELOPMENT
    internal const val IS_LOG_ENABLE: Boolean = BuildConfig.IS_DEVELOPMENT
    internal const val IS_SECURE_SCREENSHOT: Boolean = false

    internal const val DB_NAME = "base_project.db"
    internal const val DB_VERSION = BuildConfig.VERSION_CODE
    internal const val PREF_STORAGE_NAME = "pref_storage_client"
    internal const val PREF_SHARED_NAME = "pref_shared_client"
    internal const val PREF_NAME = "pref_base_project"

    internal const val MIN_AGE_FOR_APPLY = -18 // it's minus. it's minus from current date
    internal const val MAX_IMAGE_SIZE = 1080
    internal const val QUESTION_DELAY: Long = 3000L // 3 sec
    internal const val TIMESTAMP_FORMAT = "dd-MMM-yyyy"
    internal const val COUNTDOWN_INTERVAL = 1000L
    internal const val IMAGE_QUALITY = 80
    internal val MIME_TYPE = arrayOf("image/png", "image/jpg", "image/jpeg")
}