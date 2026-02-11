package com.example.water_logging_app.ui.homepage.viewModel.History

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogs.data.repository.WaterLogRepositoryImpl
import com.example.water_logging_app._waterLogs.domain.modelData.WaterLogDataList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllWaterLogsViewModel @Inject constructor(
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _allWaterLogs = MutableStateFlow(WaterLogDataList())
    val allWaterLogs : StateFlow<WaterLogDataList> = _allWaterLogs.asStateFlow()

    init {
        loadAllWaterLogs()
    }

    fun loadAllWaterLogs() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }
}