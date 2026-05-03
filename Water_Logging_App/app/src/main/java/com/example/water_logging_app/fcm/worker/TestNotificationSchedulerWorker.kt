package com.example.water_logging_app.fcm.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.water_logging_app.notifications.domain.remote.modelData.TestNotifRequestData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TestNotificationSchedulerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repo : NotifRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            repo.testNotifRequest(
                TestNotifRequestData(
                    fcmToken = inputData.getString(FCM_TOKEN) ?: return Result.failure(),
                    title = inputData.getString(TITLE) ?: "",
                    body = inputData.getString(BODY) ?: ""
                )
            )
            Result.success()
        }
        catch (e : Exception) {
            Log.e("TestWorker", "Failed: ${e.message}", e)
            Result.retry()
        }
    }

    companion object {
        const val FCM_TOKEN = "fcmToken"
        const val TITLE = "title"
        const val BODY = "body"
    }
}