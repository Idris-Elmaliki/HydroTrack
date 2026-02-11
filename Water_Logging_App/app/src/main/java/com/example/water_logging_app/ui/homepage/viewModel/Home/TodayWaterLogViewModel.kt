package com.example.water_logging_app.ui.homepage.viewModel.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogData
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayWaterLogViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _todayWaterLogs = MutableStateFlow(WaterLogDataList())
    var todayWaterLogs : StateFlow<WaterLogDataList> = _todayWaterLogs.asStateFlow()

    init {
        loadTodayWaterLogs()
    }

    fun loadTodayWaterLogs() {
        viewModelScope.launch {
            try {

            }
            catch (e : Exception) {

            }
        }
    }

    fun insertWaterLogData(
        waterAmount : Int,
        measurementType : String
    ) {
        viewModelScope.launch {
            try {

            }
            catch (e : Exception) {

            }
        }
    }
}
