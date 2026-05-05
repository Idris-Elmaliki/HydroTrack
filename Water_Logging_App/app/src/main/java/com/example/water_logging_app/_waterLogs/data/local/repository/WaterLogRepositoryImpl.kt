package com.example.water_logging_app._waterLogs.data.local.repository

import com.example.water_logging_app._waterLogs.data.local.dao.WaterLogDAO
import com.example.water_logging_app._waterLogs.data.local.entity.WaterLogEntity
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.repository.WaterLogRepository
import com.example.water_logging_app.time.TimeConversion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterLogRepositoryImpl @Inject constructor(
    private val waterLogDao: WaterLogDAO
) : WaterLogRepository {
    override suspend fun upsertLoggedWaterData(waterDataList: List<WaterLogData>) {
        waterLogDao.insertLoggedWaterData(waterDataList.toListWaterLogEntity())
    }

    override suspend fun getWaterDataASC(): Flow<List<WaterLogData>> {
        return waterLogDao.getWaterDataASC().toWaterLogDataList()
    }

    override suspend fun getWaterDataDSC(): Flow<List<WaterLogData>> {
        return waterLogDao.getWaterDataDSC().toWaterLogDataList()
    }

    override suspend fun getWaterDataByDate(date: String): Flow<List<WaterLogData>> {
        return waterLogDao.getWaterDataByDay(date).toWaterLogDataList()
    }

    override suspend fun getWeeklyLoggedWaterData(
        startDate: String,
        endDate: String
    ): Flow<List<WaterLogData>> {
        return waterLogDao.getWeeklyWaterLog(
            startDate = startDate,
            endDate = endDate
        ).toWaterLogDataList()
    }

    override suspend fun deleteLoggedWaterData(waterData: WaterLogData) {
        waterLogDao.deleteWaterData(waterData.toWaterLogEntity())
    }
}

private fun Flow<List<WaterLogEntity>>.toWaterLogDataList(): Flow<List<WaterLogData>> {
    return map { entityList ->
        entityList.map { index ->
            WaterLogData(
                amountOfWater = index.amountOfWater,
                timeOfInput = TimeConversion.getLocalDateTimeFromStringR(index.timeOfInput)
            )
        }
    }
}

private fun List<WaterLogData>.toListWaterLogEntity() : List<WaterLogEntity> {
    val entityList : MutableList<WaterLogEntity> = mutableListOf()

    forEach { index ->
        entityList.add(
            WaterLogEntity(
                amountOfWater = index.amountOfWater,
                timeOfInput = TimeConversion.getStringFromLocalDateTimeR(index.timeOfInput)
            )
        )
    }

    return entityList.toList()
}

private fun WaterLogData.toWaterLogEntity(): WaterLogEntity {
    return WaterLogEntity(
        amountOfWater = amountOfWater,
        timeOfInput = TimeConversion.getStringFromLocalDateTimeR(timeOfInput)
    )
}
