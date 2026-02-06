package com.example.water_logging_app._waterLogs.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_logging_app._waterLogs.data.local.dao.WaterInfoDAO
import com.example.water_logging_app._waterLogs.data.local.entity.WaterInfoEntity


@Database(
    entities = [WaterInfoEntity::class],
    version = 1
)
abstract class WaterInfoDatabase : RoomDatabase() {
    abstract fun waterLogDao() : WaterInfoDAO
}
