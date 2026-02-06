package com.example.water_logging_app.ui.viewmodel.dataclasses

data class WaterLogDataList(
    val isLoading : Boolean = true,
    val error : String? = null,

    val waterInfoList : List<WaterLogDataUiState> = emptyList()
)
data class WaterLogDataUiState(
    val amountOfWater : Int? = null,
    val measurementType : String? = null,
    val timeOfInput : String,
)
