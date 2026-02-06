package com.example.water_logging_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.repository.WaterRepository
import com.example.water_logging_app.ui.viewmodel.dataclasses.WaterLogDataList
import com.example.water_logging_app.ui.viewmodel.dataclasses.WaterLogDataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    val repository : WaterRepository
) : ViewModel() {

    var _uistate = MutableStateFlow(
        WaterLogDataList()
    )

    val uistate : StateFlow<WaterLogDataList> = _uistate.asStateFlow()

    init {
        loadWaterLogHistoryASC()
    }

    fun loadWaterLogHistoryASC() {
        try {
            viewModelScope.launch {
                repository.getWaterLogInfoHistoryASC().collect { waterInfoList ->
                    val uiStateList = waterInfoList.map { info ->
                        WaterLogDataUiState(
                            amountOfWater = info.amountOfWater,
                            measurementType = info.measurement,
                            timeOfInput = info.timeOfInput,
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
        }
        catch (e : Exception) {
            _uistate.update { currentState ->
                currentState.copy(
                    error = e.message
                )
            }
        }
    }

    fun loadWaterLogHistoryDSC() {
        try {
            viewModelScope.launch {
                repository.getWaterLogInfoHistoryDSC().collect { waterInfoList ->
                    val uiStateList = waterInfoList.map { info ->
                        WaterLogDataUiState(
                            amountOfWater = info.amountOfWater,
                            measurementType = info.measurement,
                            timeOfInput = info.timeOfInput,
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