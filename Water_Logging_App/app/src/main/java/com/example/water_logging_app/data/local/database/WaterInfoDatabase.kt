package com.example.water_logging_app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.water_logging_app.DataAccessObject
import com.example.water_logging_app.data.local.entity.WaterInfos


@Database(
    entities = [WaterInfos::class],
    version = 1
)
abstract class WaterInfoDatabase : RoomDatabase() {
    abstract fun waterLogDao() : DataAccessObject
}
