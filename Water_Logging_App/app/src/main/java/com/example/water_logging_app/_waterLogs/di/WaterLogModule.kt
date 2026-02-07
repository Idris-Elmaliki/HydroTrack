package com.example.water_logging_app._waterLogs.di

import android.content.Context
import androidx.room.Room
import com.example.water_logging_app._waterLogs.data.local.dao.WaterLogDAO
import com.example.water_logging_app._waterLogs.data.local.database.WaterInfoDatabase
import com.example.water_logging_app._waterLogs.data.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.repository.WaterLogRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaterLogModule {
    @Provides
    @Singleton
    fun getWaterLogDatabase(
        @ApplicationContext context: Context
    ) : WaterInfoDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WaterInfoDatabase::class.java,
            name ="water_log_database"
        ).build()
    }

    @Provides
    @Singleton
    fun getWaterLogDao(
        waterInfoDatabase: WaterInfoDatabase
    ) : WaterLogDAO {
        return waterInfoDatabase.getWaterLogDao()
    }

    @Provides
    @Singleton
    fun getWaterLogRepository(
        waterLogDAO: WaterLogDAO
    ) : WaterLogRepository {
        return WaterLogRepositoryImpl(waterLogDAO)
    }
}