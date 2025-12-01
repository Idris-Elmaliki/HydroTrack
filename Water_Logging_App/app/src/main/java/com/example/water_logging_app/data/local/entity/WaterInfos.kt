package com.example.water_logging_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Water_Info_Table")
data class WaterInfos(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val amountOfWater: Int,
    val measurement : String,
    val timeOfInput : String,
)