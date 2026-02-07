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
    suspend fun insertLoggedWaterData(waterInfo : WaterLogEntity)

    @Delete
    suspend fun deleteWaterData(waterInfo : WaterLogEntity)

    // The type to be a Flow<List> ensures any changes in the table gets updated in the Ui
    // + it keeps it a sync

    /*
    * Will be used in the History channel
    * (We want the user to be able to choose different sorting options)
    */

    @Query("SELECT * FROM water_info_table ORDER BY timeOfInput ASC")
    fun getWaterDataByTimeListASC() : Flow<List<WaterLogEntity>>

    @Query("SELECT * FROM water_info_table ORDER BY timeOfInput DESC")
    fun getWaterDataByTimeListDSC() : Flow<List<WaterLogEntity>>

    /*
    * With the power of ISOs we can do something like this!
    *
    * I was able to compare today (the currentDay) to the new ISO string format within the database.
    * What the Query is saying is to find the timeOfInput that is == to current Day
    * And what the % does is actually different, it is NOT the modulus operator!
    * Instead it tells ROOM to ignore all the data passed the data we called for in today parameter!
    *
    * ISO strings are a VERY powerful tool!
    */
    @Query("SELECT * FROM water_info_table WHERE timeOfInput LIKE :today || '%' ORDER BY timeOfInput ASC")
    fun getWaterDataByDay(today : String) : Flow<List<WaterLogEntity>>
}