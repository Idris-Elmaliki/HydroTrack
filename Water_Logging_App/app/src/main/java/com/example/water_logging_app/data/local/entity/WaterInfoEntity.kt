package com.example.water_logging_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Water_Info_Table")
data class WaterInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long? = null,

    val amountOfWater: Int,
    val measurement : String? = null,
    val timeOfInput : String? = null
)