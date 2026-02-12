package com.example.water_logging_app.ui.homepage.viewModel.Settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl
) : ViewModel() {
    private var _settingsData = MutableStateFlow(UserPreferenceData(
        name = "",
        dailyGoal = 0,
        preferredMeasurement = ""
    ))
    val settingsData : StateFlow<UserPreferenceData> = _settingsData.asStateFlow()

    init {
        loadSettingsData()
    }

    fun loadSettingsData() {
        viewModelScope.launch {
            try {
                val userData = repo.getUserPreference()
                _settingsData.update { data ->
                    data.copy(
                        name = userData.name,
                        dailyGoal = userData.dailyGoal,
                        preferredMeasurement = userData.preferredMeasurement
                    )
                }

            } catch (e: Exception) {
                _settingsData.update { data ->
                    data.copy(
                        error = e.message.toString()
                    )
                }
            }
        }
    }


}