package com.example.baseproject.utils

object NetworkConstants {
    internal const val AUTH_TOKEN = "Auth-Token"
    internal const val BEARER = "Bearer"
    internal const val USER_AGENT = "User-Agent"
    internal const val ANDROID = "Android"
    internal const val AUTHORIZATION = "Authorization"
    internal const val AUTHORIZATION_VALUE = "key=AAAAlETGOrc:APA91bECI551JAi-ShVrk9XRidJN4IwBtQGAvG0P1QVm-c5vbk9ZTy_9Nc6OZrkuao2k9vkK6_BEo6pvrhqk8Mk6PIGiq3YCUnJw9XkVY5LQksOh0vscVun5k7Ac1Xr3z4IExYThGquk"
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