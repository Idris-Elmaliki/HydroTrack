package com.example.water_logging_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.repository.WaterRepository
import com.example.water_logging_app.ui.viewmodel.dataclasses.WaterLogDataUiState
import com.example.water_logging_app.ui.viewmodel.dataclasses.WaterLogDataList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DailyWaterLogViewModel(
    val repository : WaterRepository
) : ViewModel() {
    private var _uistate = MutableStateFlow(
        WaterLogDataList()
    )

    val uistate : StateFlow<WaterLogDataList> = _uistate.asStateFlow()

    init {
        loadWaterDataForTheDay()
    }

    fun loadWaterDataForTheDay() {
        // makes the function async (coroutine)
        viewModelScope.launch {
            // ISO string format
            val currentTime = LocalDateTime.now().toString()
            val today = currentTime.take(10)

            try {
                // get the data from the repository
                repository.getDailyWaterLogInfo(
                    todaysDate = today
                ).collect { waterInfoList ->
                /*
                * We need the ui state to know something got updated.
                * So even though we aren't changing any values, we will still need to update the ui state with .copy()
                */

                /*
                * The repository function returns a WaterInfoEntityList
                * So we need to use .map{} to everything into a list of WaterLogDataUiState
                */
                    val uiStateList = waterInfoList.map { info ->
                        WaterLogDataUiState(
                            amountOfWater = info.amountOfWater,
                            measurementType = info.measurement,
                            timeOfInput = info.timeOfInput
                        )
                    }

                    _uistate.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            waterInfoList = uiStateList
                        )
                    }
                }
            }
            catch (e : Exception) {
                _uistate.update { currentState ->
                    currentState.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    // We can test whether or not the waterAmount is valid at the ui side
    fun insertWaterLogData(
        waterAmount : Int,
        measurementType : String
    ) {
        viewModelScope.launch {
            try {
                // ISO string format
                val currentTime = LocalDateTime.now().toString()

                repository.insertWaterData(
                    waterAmount = waterAmount,
                    measurementType = measurementType,
                    currentTime = currentTime,
                )

                _uistate.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        waterInfoList = currentState.waterInfoList + WaterLogDataUiState(
                            amountOfWater = waterAmount,
                            measurementType = measurementType,
                            timeOfInput = currentTime,
                        )
                    )
                }
            }
            catch (e : Exception) {
                _uistate.update { currentState ->
                    currentState.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}