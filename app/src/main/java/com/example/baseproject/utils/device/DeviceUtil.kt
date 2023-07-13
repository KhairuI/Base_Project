@file:Suppress("DEPRECATION")

package com.example.baseproject.utils.device

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.util.*

object DeviceUtil {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String = try {
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    } catch (e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
        e.printStackTrace()
        ""
    }

    fun getModel():String? = Build.MODEL

    fun getProduct():String? = Build.PRODUCT

    fun getManufacturer():String? = Build.MANUFACTURER

    @SuppressLint("HardwareIds")
    fun getSerial():String? = Build.SERIAL

    fun getRelease():String? = Build.VERSION.RELEASE

    fun getSdkVersion(): String = Build.VERSION.SDK_INT.toString()

    fun getTimeZone():String? = TimeZone.getDefault().id

    fun getFingerPrint():String? = Build.FINGERPRINT


}

