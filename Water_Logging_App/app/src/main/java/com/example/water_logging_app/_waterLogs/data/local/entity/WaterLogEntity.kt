package com.example.water_logging_app._waterLogs.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Water_Log_Table")
data class WaterLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,

    val amountOfWater: Int,
    val measurement : String,
    val timeOfInput : String,
)