package com.example.water_logging_app._waterLogs.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import kotlinx.coroutines.flow.Flow

/*
* This is the Data Access Object for the WaterLogEntity
*
* This essentially hosts the database Logic
*/

@Dao
interface WaterLogDAO {
    // inserts AND updates data
    @Upsert
    suspend fun insertLoggedWaterData(waterInfoList : List<WaterLogEntity>)

    @Delete
    suspend fun deleteWaterData(waterInfo : WaterLogEntity)

    @Query("SELECT * FROM water_log_table ORDER BY timeOfInput ASC")
    fun getWaterDataASC() : Flow<List<WaterLogEntity>>

    @Query("SELECT * FROM water_log_table ORDER BY timeOfInput DESC")
    fun getWaterDataDSC() : Flow<List<WaterLogEntity>>

    @Query("SELECT * FROM water_log_table WHERE timeOfInput LIKE :today || '%' ORDER BY timeOfInput ASC")
    fun getWaterDataByDay(today : String) : Flow<List<WaterLogEntity>>

    @Query("SELECT * FROM water_log_table WHERE timeOfInput BETWEEN :startDate AND :endDate")
    fun getWeeklyWaterLog(startDate : String, endDate : String) : Flow<List<WaterLogEntity>>
}