package com.example.water_logging_app.fcm.data

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import androidx.core.app.NotificationCompat
import com.example.water_logging_app.R
import com.example.water_logging_app.notifications.data.remote.repositoryImpl.NotifRepositoryImpl
import com.example.water_logging_app.notifications.domain.remote.modelData.RegisterDeviceData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint // Don't forget, FCM is a service so Android made it, hence @AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var repo: NotifRepository

    @SuppressLint("HardwareIds")
    override fun onNewToken(fcmToken: String) {
        super.onNewToken(fcmToken) // creates a new token

        val deviceId = Settings.Secure.getString(contentResolver, ANDROID_ID)
        CoroutineScope(Dispatchers.IO).launch {
            repo.registerUserDevice(
                RegisterDeviceData(
                    installationId = deviceId,
                    fcmToken = fcmToken
                )
            )
        }
    }

    // this function is used to create the notification on the foreground!
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // First we extract title and body from the message
        val notifTitle = message.notification?.title
        val notifBody = message.notification?.body

        // Second we build the notification
        val notification = NotificationCompat.Builder(this, "water_logging_reminders")
            .setSmallIcon(R.drawable.bottleofwater)
            .setContentTitle(notifTitle)
            .setContentText(notifBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // dismisses notification when tapped
            .build()

        // Third we display the notification
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }
}