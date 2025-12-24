package com.example.water_logging_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.water_logging_app.data.local.entity.WaterInfoEntity
import kotlinx.coroutines.flow.Flow

/*
* This is the Data Access Object for the WaterInfoEntity
*
* This essentially hosts the database Logic
*/

@Dao
interface WaterInfoDAO {
    // inserts AND updates data
    @Upsert
    suspend fun insertWaterLogData(waterInfo : WaterInfoEntity)

    @Delete
    suspend fun deleteWaterData(waterInfo : WaterInfoEntity)

    // The type to be a Flow<List> ensures any changes in the table gets updated in the Ui
    // + it keeps it a sync

    /*
    * Will be used in the History channel
    * (We want the user to be able to choose different sorting options)
    */
    @Query("SELECT * FROM water_info_table ORDER BY timeOfInput ASC")
    fun getWaterDataByTimeListASC() : Flow<List<WaterInfoEntity>>

    @Query("SELECT * FROM water_info_table ORDER BY timeOfInput DESC")
    fun getWaterDataByTimeListDSC() : Flow<List<WaterInfoEntity>>

    // Used to get the data for the current day as a List for the Home Ui Page
    @Query("SELECT * FROM water_info_table WHERE timeOfInput >= :startOfToday AND timeOfInput <= :endOfToday")
    fun getWaterDataByDay(startOfToday : String, endOfToday : String) : Flow<List<WaterInfoEntity>>
}