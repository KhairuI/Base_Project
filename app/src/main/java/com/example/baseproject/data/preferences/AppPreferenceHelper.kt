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
        private const val PREF_KEY_USER_PHOTO = "pref_key_user_photo"
    }

    private val ctx = context

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun isLoggedIn(): Boolean = mPrefs.getBoolean(PREF_KEY_LOGGED_IN, false)

    override fun setLoggedIn(isLoggedIn: Boolean) = mPrefs.edit()
        .putBoolean(PREF_KEY_LOGGED_IN, isLoggedIn).apply()

}
