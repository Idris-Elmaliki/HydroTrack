package com.example.water_logging_app.preferenceData.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PreferenceWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: UserPreferenceRepositoryImpl
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val data = UserPreferenceData(
                firstName = inputData.getString(KEY_FIRST_NAME) ?: "",
                lastName = inputData.getString(KEY_LAST_NAME) ?: "",
                userName = inputData.getString(KEY_USERNAME) ?: "",
                age = inputData.getString(KEY_AGE) ?: "",
                gender = inputData.getString(KEY_GENDER) ?: "",
                unitOfMeasurement = inputData.getString(KEY_UNIT) ?: "",
                height = inputData.getFloat(KEY_HEIGHT, 0f),
                weight = inputData.getFloat(KEY_WEIGHT, 0f),
                activityLevel = inputData.getString(KEY_ACTIVITY_LEVEL) ?: "",
                dailyGoal = inputData.getLong(KEY_DAILY_GOAL, 0L)
            )

            repository.insertUserPreference(data)

            Result.success()
        }
        catch (e : Exception) {
            Result.retry()
        }
    }

    companion object {
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_USERNAME = "username"
        const val KEY_AGE = "age"
        const val KEY_GENDER = "gender"
        const val KEY_UNIT = "unit"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_DAILY_GOAL = "daily_goal"
    }
}

