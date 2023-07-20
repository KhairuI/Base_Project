package com.example.baseproject.utils.extension

import android.text.format.DateFormat
import android.util.Log
import android.view.View
import com.example.baseproject.BuildConfig
import com.example.baseproject.R
import com.example.baseproject.utils.AppConstants
import com.google.android.material.textview.MaterialTextView

inline fun development(block: () -> Unit) {
    if (BuildConfig.IS_DEVELOPMENT) {
        block()
    }
}

inline fun debug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

inline fun release(block: () -> Unit) {
    if (!BuildConfig.IS_DEVELOPMENT) {
        block()
    }
}

fun MaterialTextView.buildTime(): String = if (AppConstants.IS_BUILD_SHOW) {
    Log.d("xxx", "buildTime: ${BuildConfig.BUILD_TIME.toLong()}")
    visibility = View.VISIBLE
    text= ""
    context.getString(
        R.string.build_time,
        BuildConfig.VERSION_NAME,
        DateFormat.format("dd-MMM-yyyy, hh:mm a", BuildConfig.BUILD_TIME.toLong())
    )
} else {
    Log.d("xxx", "buildTime: empty")
    visibility = View.GONE
    ""
}