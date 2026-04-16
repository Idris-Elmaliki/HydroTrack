package com.example.water_logging_app.preferenceData.worker.di

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.worker.PreferenceWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceWorkerSchedule @Inject constructor(
    @param:ApplicationContext private val applicationContext: Context,
) {
    fun enqueueSaveUserPreference(
        userData : UserPreferenceData
    ) {
        val input = Data.Builder()
            .putString(PreferenceWorker.KEY_FIRST_NAME, userData.firstName)
            .putString(PreferenceWorker.KEY_LAST_NAME, userData.lastName)
            .putString(PreferenceWorker.KEY_USERNAME, userData.userName)
            .putString(PreferenceWorker.KEY_AGE, userData.age)
            .putString(PreferenceWorker.KEY_GENDER, userData.gender)
            .putString(PreferenceWorker.KEY_UNIT, userData.unitOfMeasurement)
            .putFloat(PreferenceWorker.KEY_HEIGHT, userData.height)
            .putFloat(PreferenceWorker.KEY_WEIGHT, userData.weight)
            .putString(PreferenceWorker.KEY_ACTIVITY_LEVEL, userData.activityLevel)
            .putLong(PreferenceWorker.KEY_DAILY_GOAL, userData.dailyGoal)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PreferenceWorker>()
            .setInputData(input)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }
}