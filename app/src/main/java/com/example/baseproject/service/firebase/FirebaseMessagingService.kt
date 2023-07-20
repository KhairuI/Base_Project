package com.example.baseproject.service.firebase

import android.util.Log
import com.example.baseproject.data.preferences.AppPreferenceHelper
import com.example.baseproject.ui.base.helper.BroadcastHelper
import com.example.baseproject.ui.base.helper.NotificationHelper
import com.example.baseproject.utils.AppConstants
import com.example.baseproject.utils.extension.safeLet
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val pref = AppPreferenceHelper(this, AppConstants.PREF_NAME)

        Log.d("xxx", "title: ${remoteMessage.notification?.title}")
        Log.d("xxx", "body: ${remoteMessage.notification?.body}")


        remoteMessage.notification?.let { message ->
            safeLet(message.title, message.body) { title, body ->
                BroadcastHelper.broadcast(this, null)

                if(pref.getNotificationPref()){
                    NotificationHelper.showNotification(
                        context = this, titleText = title, bodyText = body
                    )
                }
            }
        }
    }

    override fun onNewToken(token: String) {

    }
}