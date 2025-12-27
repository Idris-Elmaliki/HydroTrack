package com.example.water_logging_app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_logging_app.data.local.dao.UserPreferenceDAO
import com.example.water_logging_app.data.local.entity.UserPreferenceEntity

@Database(
    entities = [UserPreferenceEntity::class],
    version  = 1
)
abstract class UserPreferenceDatabase : RoomDatabase() {
    abstract fun userPreferenceDao() : UserPreferenceDAO
}