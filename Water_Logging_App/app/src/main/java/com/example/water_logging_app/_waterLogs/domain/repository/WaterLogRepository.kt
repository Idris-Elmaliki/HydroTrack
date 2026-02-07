package com.example.water_logging_app._waterLogs.domain.repository

import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity

interface WaterLogRepository {
    suspend fun upsertLoggedWaterData(waterData : WaterLogEntity)

    suspend fun deleteLoggedWaterData(waterData : WaterLogEntity)
}