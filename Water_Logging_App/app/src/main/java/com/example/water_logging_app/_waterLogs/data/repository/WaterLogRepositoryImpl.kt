package com.example.water_logging_app._waterLogs.data.repository

import com.example.water_logging_app._waterLogs.data.local.dao.WaterLogDAO
import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.repository.WaterLogRepository

class WaterLogRepositoryImpl(
    private val waterLogDao: WaterLogDAO
) : WaterLogRepository {
    override suspend fun upsertLoggedWaterData(waterData: WaterLogData) {
        waterLogDao.insertLoggedWaterData(waterData.toWaterLogEntity())
    }

    override suspend fun getWaterDataASC(): List<WaterLogData> {
        return waterLogDao.getWaterDataASC().toWaterLogDataList()
    }

    override suspend fun getWaterDataDSC(): List<WaterLogData> {
        return waterLogDao.getWaterDataDSC().toWaterLogDataList()
    }

    override suspend fun getWaterDataByDate(date: String): List<WaterLogData> {
        // return waterLogDao.getWaterDataByDate(date).toWaterLogDataList()
        // I wanted to have a search up / filter option for this (Will be made soon)
        TODO("Not yet implemented")
    }

    override suspend fun deleteLoggedWaterData(waterData: WaterLogData) {
        waterLogDao.deleteWaterData(waterData.toWaterLogEntity())
    }
}

private fun List<WaterLogEntity>.toWaterLogDataList(): List<WaterLogData> {
    val waterDataList = mutableListOf<WaterLogData>()

    forEach { index ->
        waterDataList.add(
            WaterLogData(
                amountOfWater = index.amountOfWater,
                measurementType = index.measurement,
                timeOfInput = index.timeOfInput
            )
        )
    }

    return waterDataList
}


private fun WaterLogData.toWaterLogEntity(): WaterLogEntity {
    return WaterLogEntity(
        amountOfWater = amountOfWater,
        measurement = measurementType,
        timeOfInput = timeOfInput
    )
}
