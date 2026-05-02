package com.example.water_logging_app.fcm.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkerParameters
import com.example.water_logging_app.notifications.data.local.NotificationDataStoreManager
import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationSchedulerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repo : NotifRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            repo.sendNotifRequest(
                NotifRequestData(
                    installationId = inputData.getString(KEY_INSTALLATION_ID) ?: return Result.failure(),
                    title = inputData.getString(TITLE) ?: "",
                    body = inputData.getString(BODY) ?: ""
                )
            )
            Result.success()
        }
        catch (e : Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_INSTALLATION_ID = "installationId"
        const val TITLE = "title"
        const val BODY = "body"
    }
}