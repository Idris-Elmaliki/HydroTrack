package com.example.water_logging_app.preferenceData.data.repository

import android.content.Context
import androidx.room.Room
import com.example.water_logging_app.preferenceData.data.local.database.UserPreferenceDatabase
import com.example.water_logging_app.preferenceData.data.local.entity.UserPreferenceEntity

class PreferenceRepositoryImpl(
    private val dao : UserPreferenceDatabase
) {
    
}