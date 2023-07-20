package com.example.baseproject.utils

object NetworkConstants {
    internal const val AUTH_TOKEN = "Auth-Token"
    internal const val BEARER = "Bearer"
    internal const val USER_AGENT = "User-Agent"
    internal const val ANDROID = "Android"
    internal const val AUTHORIZATION = "Authorization"
    internal const val AUTHORIZATION_VALUE = "key=AAAAmtxjqCw:APA91bE_U49iRC8ob5hw-fWnXSKHZbdGlrauiM-yHyl_Jqp4HGRJIBhCFxagdt1_SwZRwTbXMU1-iDrYEoKDDsyUZvmKNagFxk2ipz5pU6qaAd0bRrK8R0yLbUkjl2WR2JrNMSHNWfKK"
    internal const val ACCEPT = "Accept"
    internal const val CONTENT_TYPE = "Content-Type"
    internal const val APPLICATION_JSON = "application/json"
    internal const val CHUCK_MAX_CONTENT_LENGTH = 250000L

    internal const val APP_KEY = "app_key"
    internal const val PACKAGE = "package"
    internal const val VERSION = "version"
    internal const val LANGUAGE = "language"
    internal const val DEVICE_ID = "device_id"
    internal const val CONNECTION = "Connection"
    internal const val KEEP_ALIVE = "keep-alive"
    internal const val CLOSE = "close"

    internal const val TIMEOUT_CONNECTION: Long = 30 // 30 seconds
    internal const val TIMEOUT_READ: Long = 30 // 30 seconds
    internal const val TIMEOUT_WRITE: Long = 30 // 30 seconds
    internal const val TIMEOUT_MAX: Long = 180 // 180 seconds
}