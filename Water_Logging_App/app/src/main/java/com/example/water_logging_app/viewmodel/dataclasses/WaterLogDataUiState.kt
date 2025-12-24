package com.example.water_logging_app.viewmodel.dataclasses

data class WaterLogDataList(
    val waterInfoList : List<WaterLogDataUiState> = emptyList()
)
data class WaterLogDataUiState(
    val amountOfWater : Int? = null,
    val measrementType : String? = null,
    val timeOfInput : String
)