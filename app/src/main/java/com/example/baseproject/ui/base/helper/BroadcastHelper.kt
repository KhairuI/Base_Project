package com.example.baseproject.ui.base.helper

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.baseproject.BuildConfig

object BroadcastHelper {

    private const val NOTIFICATION_SERVICE_ACTION = BuildConfig.APPLICATION_ID + ".notification"
    private const val BROADCAST_PARSE_DATA = "message"

    fun broadcast(context: Context, message: String?) = Intent(NOTIFICATION_SERVICE_ACTION).apply {
        putExtra(BROADCAST_PARSE_DATA, message)

        if (isAppRunning(context)) {
            LocalBroadcastManager.getInstance(context)
                .sendBroadcast(this)
        }
    }

    private fun isAppRunning(context: Context): Boolean {
        val services =
            (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses
        return services.firstOrNull { it.processName.equals(context.packageName, true) } != null
    }
}