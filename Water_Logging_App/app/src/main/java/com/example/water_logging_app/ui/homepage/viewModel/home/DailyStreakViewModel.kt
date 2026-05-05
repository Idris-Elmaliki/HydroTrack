package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app._waterLogStreak.data.DailyStreakDataStoreManager
import com.example.water_logging_app._waterLogStreak.domain.DailyStreakData
import com.example.water_logging_app._waterLogs.data.local.repository.WaterLogRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyStreakViewModel @Inject constructor(
    private val dataStore : DailyStreakDataStoreManager,
    private val repo : WaterLogRepositoryImpl
) : ViewModel() {
    private var _dailyStreak = MutableStateFlow(DailyStreakData())
    val dailyStreak = _dailyStreak.asStateFlow()

    init {
        loadDailyStreakData()
    }

    private fun loadDailyStreakData() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                dataStore.getCurrentDailyStreak(),
                dataStore.getMaxDailyStreak()
            ) { current, max ->
                _da
            }.collect {

            }
        }
    }
}