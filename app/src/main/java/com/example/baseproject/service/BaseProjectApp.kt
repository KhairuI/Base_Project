package com.example.baseproject.service

import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.baseproject.utils.helper.LocaleManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseProjectApp : MultiDexApplication() {

    companion object {
        var positionQuestion: Int = 0
        var localeManager: LocaleManager? = null
    }

    override fun attachBaseContext(base: Context) {
        localeManager = LocaleManager(base)
        super.attachBaseContext(localeManager?.setLocale(base))
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager?.setLocale(this)
    }
}