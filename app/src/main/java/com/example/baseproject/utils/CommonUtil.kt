package com.example.baseproject.utils

import android.text.format.DateFormat
import com.example.baseproject.BuildConfig
import com.example.baseproject.R
import com.example.baseproject.utils.arch.UiText

object CommonUtil {

    fun buildTime(): UiText {
        return UiText.StringResource(
            R.string.build_time,
            BuildConfig.VERSION_NAME,
            DateFormat.format("dd-MMM-yyyy, hh:mm a", BuildConfig.BUILD_TIME.toLong())
        )
    }

}