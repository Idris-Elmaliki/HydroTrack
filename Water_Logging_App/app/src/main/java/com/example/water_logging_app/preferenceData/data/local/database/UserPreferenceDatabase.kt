package com.example.water_logging_app.preferenceData.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity
import com.example.water_logging_app.preferenceData.data.local.dao.UserPreferenceDAO

@Database(
    entities = [UserPreferenceEntity::class],
    version  = 1
)
abstract class UserPreferenceDatabase : RoomDatabase() {
    abstract fun getUserPreferenceDao() : UserPreferenceDAO
}