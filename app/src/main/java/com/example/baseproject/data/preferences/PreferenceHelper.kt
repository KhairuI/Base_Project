package com.example.baseproject.data.preferences

interface PreferenceHelper {

    fun isLoggedIn(): Boolean

    fun setLoggedIn(isLoggedIn: Boolean)

    fun getDeviceFcm(): String

    fun setDeviceFcm(deviceFcm: String)

    fun getNotificationPref(): Boolean

    fun setNotificationPref(isNotificationAllow: Boolean)

    fun getFirebaseUid(): String

    fun setFirebaseUid(uid: String)

}