package com.example.water_logging_app.preferenceData.di

import android.content.Context
import androidx.room.Room
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.water_logging_app.preferenceData.data.local.dao.UserPreferenceDAO
import com.example.water_logging_app.preferenceData.data.local.database.UserPreferenceDatabase
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import com.example.water_logging_app.preferenceData.worker.PreferenceWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPreferenceModule {
    @Provides
    @Singleton
    fun getUserPreferenceDB(
        @ApplicationContext applicationContext: Context
    ) : UserPreferenceDatabase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass= UserPreferenceDatabase::class.java,
            name ="user_preference_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getUserPreferenceDao(
        userPreferenceDB: UserPreferenceDatabase
    ) : UserPreferenceDAO {
        return userPreferenceDB.getUserPreferenceDao()
    }
}