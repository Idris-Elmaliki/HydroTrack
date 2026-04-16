package com.example.water_logging_app.photoPicker.worker.di

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.water_logging_app.photoPicker.worker.PhotoWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoWorkerSchedule @Inject constructor(
    @param:ApplicationContext private val applicationContext: Context,
) {
    fun enqueueSaveUserPhoto(
        userData : String
    ) {
        val input = Data.Builder()
            .putString(PhotoWorker.FILE_PATH, userData)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PhotoWorker>()
            .setInputData(inputData = input)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}