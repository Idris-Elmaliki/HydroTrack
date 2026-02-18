package com.example.water_logging_app.ui.homepage.viewModel.settings

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
    private var _settingsData = MutableStateFlow(UserPreferenceData())
    val settingsData : StateFlow<UserPreferenceData> = _settingsData.asStateFlow()

    init {
        loadSettingsData()
    }

    fun loadSettingsData() {
        viewModelScope.launch {
            try {
                val userData = repo.getUserPreference()?: UserPreferenceData()

                _settingsData.update { data ->
                    data.copy(
                        name = userData.name,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        isMetric = userData.isMetric
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