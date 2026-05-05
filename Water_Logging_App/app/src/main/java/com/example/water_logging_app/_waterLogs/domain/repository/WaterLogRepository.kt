package com.example.water_logging_app._waterLogs.domain.repository

import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface WaterLogRepository {
    suspend fun upsertLoggedWaterData(waterDataList : List<WaterLogData>)

    suspend fun getWaterDataASC() : Flow<List<WaterLogData>>

    suspend fun getWaterDataDSC() : Flow<List<WaterLogData>>

    suspend fun getWaterDataByDate(date : String) : Flow<List<WaterLogData>>

    suspend fun getWeeklyLoggedWaterData(
        startDate : String,
        endDate : String
    ) : Flow<List<WaterLogData>>

    suspend fun deleteLoggedWaterData(waterData : WaterLogData)
}