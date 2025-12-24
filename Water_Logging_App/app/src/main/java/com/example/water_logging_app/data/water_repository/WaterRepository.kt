package com.example.water_logging_app.data.water_repository

import android.content.Context
import com.example.water_logging_app.data.local.database.WaterInfoDatabase
import androidx.room.Room
import com.example.water_logging_app.data.local.entity.WaterInfoEntity
import kotlinx.coroutines.flow.Flow

/*
* This is the repository for the WaterInfoDatabase
*
* A repository like for implementing retrofit allows the viewModel and the database to interact with one another.
* The repository adds the implementation and the logic behind the manipulation of the database with ROOM!
*/

class WaterRepository(
    private val repoContext : Context
) {
     /*
     * Create a singleton for the database.
     * This ensures that there is only one instance of the database!
     */
     private val database : WaterInfoDatabase by lazy {
        Room.databaseBuilder(
            context = repoContext.applicationContext,
            WaterInfoDatabase::class.java,
            name = "water_info_database"
        ).build()
    }

    // call the singleton!
    private val waterDao = database.waterLogDao()

    // below are the functions for the database!

    suspend fun insertWaterData(
        waterAmount : Int,
        measurementType : String
    ) {
        val waterInfo = WaterInfoEntity(
            amountOfWater = waterAmount,
            measurement = measurementType,
            // place holder
            timeOfInput = System.currentTimeMillis().toString()
        )

        waterDao.insertWaterLogData(waterInfo)
    }

     fun getDailyWaterLogInfo(
         start : String,
         end : String
     ) : Flow<List<WaterInfoEntity>> {
        return waterDao.getWaterDataByDay(
            startOfToday = start,
            endOfToday = end
        )
    }
}