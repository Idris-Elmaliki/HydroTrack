package com.example.water_logging_app.data.repository

import android.content.Context
import androidx.room.Room
import com.example.water_logging_app.data.local.database.UserPreferenceDatabase

class PreferenceRepository(
    private val repocontext : Context
) {

    private val preferenceDatabase : UserPreferenceDatabase by lazy {
        Room.databaseBuilder(
            context = repocontext.applicationContext,
            UserPreferenceDatabase::class.java,
            name = "user_preference_database"
        ).build()
    }


}