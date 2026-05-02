package com.example.water_logging_app
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/*
* In order for hilt to even know where to inject the dependencies, it needs to know where to look for the dependencies.
* This is the entry point for dagger hilt.
*
* It also inherits from Application since it lives throughout the entire app. (From OnCreate -> OnDestroyed)
* And it is created long before anything else
*/

@HiltAndroidApp
class WaterLogApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    /*
    * Notification channels must be created before any notification is sent.
    * The Application class is the ideal place since it runs once on app startup,
        guaranteeing the channel exists before any part of the app needs it.
    */

    // we call onCreate as we want this to be run on onCreate!
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // A channel groups notifications of the same type together.
        // Users can control sound, vibration, and importance per channel in system settings.
        val channel = NotificationChannel(
            "water_logging_reminders", // id of the Channel
            "Water Logging Reminders", // name of the Channel
            NotificationManager.IMPORTANCE_HIGH // the Channels importance
        ).apply {
            // shown to the user in system settings to explain what this channel is for
            description = "Daily reminders to log your water intake"
        }

        // register the channel with Android
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }
}
