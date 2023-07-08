package com.example.baseproject.ui.base

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.example.baseproject.service.BaseProjectApp
import com.example.baseproject.utils.extension.development
import com.example.baseproject.utils.extension.hideProgress

abstract class BaseActivity : AppCompatActivity() {

    open var progress: Dialog? = null

    protected abstract fun getLayoutResourceId(): View?

    protected open fun initView(): Unit = Unit

    protected open fun observeViewModel(): Unit = Unit

    open fun dev(): Unit = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutResourceId()?.let { setContentView(it) }
        initView()
        observeViewModel()
        development { dev() }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(BaseProjectApp.localeManager?.setLocale(newBase) ?: newBase)
        MultiDex.install(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        BaseProjectApp.localeManager?.setLocale(this)
    }

    override fun onDestroy() {
        progress.hideProgress()
        super.onDestroy()
    }
}