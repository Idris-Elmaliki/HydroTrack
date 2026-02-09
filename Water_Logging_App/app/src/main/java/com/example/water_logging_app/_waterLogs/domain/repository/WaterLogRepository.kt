package com.example.water_logging_app._waterLogs.domain.repository

import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData

interface WaterLogRepository {
    suspend fun upsertLoggedWaterData(waterData : WaterLogData)

    suspend fun getWaterDataASC() : List<WaterLogData>

    suspend fun getWaterDataDSC() : List<WaterLogData>

    suspend fun getWaterDataByDate(date : String) : List<WaterLogData>

    suspend fun deleteLoggedWaterData(waterData : WaterLogData)
}