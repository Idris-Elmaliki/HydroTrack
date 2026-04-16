package com.example.water_logging_app.photoPicker.worker

import android.content.Context
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.water_logging_app.photoPicker.data.repository.PhotoRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PhotoWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: PhotoRepositoryImpl
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val filePath = inputData.getString(FILE_PATH) ?: ""

            repository.saveImage(filePath.toUri())

            Result.success()
        }
        catch (e : Exception) {
            Result.retry()
        }
    }

    companion object {
        const val FILE_PATH = "file_path"
    }
}