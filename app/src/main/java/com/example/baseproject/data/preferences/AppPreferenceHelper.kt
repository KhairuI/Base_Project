package com.example.baseproject.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Named

class AppPreferenceHelper @Inject constructor(
    @ApplicationContext context: Context,
    @Named("pref_name") private val prefFileName: String,
) : PreferenceHelper {

    companion object {
        private const val PREF_KEY_LOGGED_IN = "pref_key_logged_in"
        private const val PREF_KEY_FCM_TOKEN = "pref_key_fcm_token"
        private const val PREF_KEY_ALLOW_NOTIFICATION = "pref_key_allow_notification"
        private const val PREF_KEY_FIREBASE_UID = "pref_key_firebase_uid"
    }

    private val ctx = context

    private val mPrefs: SharedPreferences =
        ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun isLoggedIn(): Boolean = mPrefs.getBoolean(PREF_KEY_LOGGED_IN, false)

    override fun setLoggedIn(isLoggedIn: Boolean) = mPrefs.edit()
        .putBoolean(PREF_KEY_LOGGED_IN, isLoggedIn).apply()

    override fun getDeviceFcm(): String = mPrefs.getString(PREF_KEY_FCM_TOKEN, "") ?: ""

    override fun setDeviceFcm(deviceFcm: String) = mPrefs.edit()
        .putString(PREF_KEY_FCM_TOKEN, deviceFcm).apply()

    override fun getNotificationPref(): Boolean = mPrefs.getBoolean(PREF_KEY_ALLOW_NOTIFICATION, true)

    override fun setNotificationPref(isNotificationAllow: Boolean) = mPrefs.edit()
        .putBoolean(PREF_KEY_ALLOW_NOTIFICATION, isNotificationAllow).apply()

    override fun getFirebaseUid(): String = mPrefs.getString(PREF_KEY_FIREBASE_UID, "") ?: ""

    override fun setFirebaseUid(uid: String) = mPrefs.edit()
        .putString(PREF_KEY_FIREBASE_UID, uid).apply()

}
