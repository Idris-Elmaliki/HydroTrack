package com.example.water_logging_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.data.repository.WaterRepository
import com.example.water_logging_app.viewmodel.dataclasses.WaterLogDataUiState
import com.example.water_logging_app.viewmodel.dataclasses.WaterLogDataList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WaterLogViewModel(
    val repository : WaterRepository
) : ViewModel() {
    private var _uistate = MutableStateFlow(
        WaterLogDataList(
            waterInfoList = listOf(
                WaterLogDataUiState(
                    amountOfWater = null,
                    measurementType = null,
                    timeOfInput = System.currentTimeMillis().toString(), // placeholder (I need to extract the specific timing, including day, month & year)
                    date = System.currentTimeMillis().toString() // placeholder (I need to extract the specific timing, including day, month & year)
                )
            )
        )
    )

    val uistate : StateFlow<WaterLogDataList> = _uistate.asStateFlow()

    fun loadWaterDataForTheDay(
        today : String
    ) {
        // makes the function async (coroutine)
        viewModelScope.launch {
            // get the data from the repository
            repository.getDailyWaterLogInfo(
                todaysDate = today
            ).collect { WaterInfoList ->
                /*
                * We need the ui state to know something got updated.
                * So even though we aren't changing any values, we will still need to update the ui state with .copy()
                */

                /*
                * The repository function returns a WaterInfoEntityList
                * So we need to use .map{} to everything into a list of WaterLogDataUiState
                */
                val uiStateList = WaterInfoList.map { info ->
                    WaterLogDataUiState(
                        amountOfWater = info.amountOfWater,
                        measurementType = info.measurement,
                        timeOfInput = info.timeOfInput.toString(),
                        date = info.date.toString()
                    )
                }

                _uistate.value = _uistate.value.copy(
                    waterInfoList = uiStateList
                )
            }
        }
    }

    // We can test whether or not the waterAmount is valid at the ui side

    fun setWaterLogData(
        waterAmount : Int,
        measurementType : String
    ) {
        viewModelScope.launch {
            repository.insertWaterData(
                waterAmount = waterAmount,
                measurementType = measurementType
            )
        }
    }
}