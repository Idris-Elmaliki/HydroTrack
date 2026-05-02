package com.example.water_logging_app.fcm.worker.di

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.water_logging_app.fcm.worker.NotificationSchedulerWorker
import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationWorkerSchedule @Inject constructor(
    @param:ApplicationContext private val applicationContext: Context,
) {
    fun enqueueNotificationData(
        notifData : NotifRequestData,
        initialDelay : Long
    ) {
        val workData = Data.Builder()
            .putString(NotificationSchedulerWorker.KEY_INSTALLATION_ID, notifData.installationId)
            .putString(NotificationSchedulerWorker.TITLE, notifData.title)
            .putString(NotificationSchedulerWorker.BODY, notifData.body)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotificationSchedulerWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
        )
            .setInputData(workData)
            .setInitialDelay(
                duration = initialDelay, timeUnit = TimeUnit.MILLISECONDS
            )
            .build()

        // just like unique_ptr
        // this class call allows us to just have a single unique instance of the workManager
        // if it is recalled/changed, the current workManager gets destroyed
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            uniqueWorkName = "daily_notification_work",
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
            request = workRequest
        )
    }
}