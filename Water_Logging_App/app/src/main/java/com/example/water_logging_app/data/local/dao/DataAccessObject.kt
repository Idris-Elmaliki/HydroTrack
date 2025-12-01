package com.example.water_logging_app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.water_logging_app.data.local.entity.WaterInfos
import kotlinx.coroutines.flow.Flow

@Dao
interface DataAccessObject {
    // inserts AND updates data
    @Upsert
    suspend fun insertWaterData(waterInfo : WaterInfos)

    @Delete
    suspend fun deleteWaterData(waterInfo : WaterInfos)

    // want the type to be a Flow<List> in ensure any changes in the table gets updated in the UI
    @Query("SELECT * FROM water_info_table ORDER BY amountOfWater ASC")
    fun getWaterDataByWaterAmount() : Flow<List<WaterInfos>>

    @Query("SELECT * FROM water_info_table ORDER BY measurement ASC")
    fun getWaterDataByMeasurement() : Flow<List<WaterInfos>>

    @Query("SELECT * FROM water_info_table ORDER BY timeOfInput ASC")
    fun getWaterDataByTime() : Flow<List<WaterInfos>>
}