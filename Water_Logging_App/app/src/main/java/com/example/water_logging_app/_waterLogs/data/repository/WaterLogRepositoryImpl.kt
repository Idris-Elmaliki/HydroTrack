package com.example.water_logging_app._waterLogs.data.repository

import com.example.water_logging_app._waterLogs.data.local.dao.WaterLogDAO
import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import com.example.water_logging_app._waterLogs.domain.repository.WaterLogRepository

class WaterLogRepositoryImpl(
    private val waterLogDao: WaterLogDAO
) : WaterLogRepository {
    override suspend fun upsertLoggedWaterData(waterData: WaterLogEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterDataASC(): List<WaterLogEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterDataDSC(): List<WaterLogEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getWaterDataByDate(date: String): List<WaterLogEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLoggedWaterData(waterData: WaterLogEntity) {
        TODO("Not yet implemented")
    }
}

