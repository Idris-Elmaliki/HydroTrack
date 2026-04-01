package com.example.water_logging_app.ui.signUpPage.viewModels.parent

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

/*
* I need to break this viewModel into separate VMs for each screen.
*/

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl
) : ViewModel() {
    private var _signUpData = MutableStateFlow(UserPreferenceData())
    val signUpData : StateFlow<UserPreferenceData> = _signUpData.asStateFlow()

    init {
        getUserData()
    }

    // this won't be posted in the ui, and instead it is only for checking!
    fun getUserData() {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val userData = repo.getUserPreference()

                if(userData != null) {
                    _signUpData.update { data ->
                        data.copy(
                            isLoading = false
                        )
                    }
                }
                else {
                    throw Exception("No data found")
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    /*
    * This is the function that we use to properly change the values of the data.
    * (When switching between measurement types in the UserData screen)
    */
    fun syncLimitWithMetric(
        upperLimits: Pair<Float, Float>, // height, weight (in this order)
    ) {
        viewModelScope.launch {
            val info = _signUpData.value

            _signUpData.update { data ->
                data.copy(
                    isLoading = false,
                    height = if(info.height > upperLimits.first) {
                        upperLimits.first
                    } else {
                        info.height
                    },
                    weight = if(info.weight > upperLimits.second) {
                        upperLimits.second
                    } else {
                        info.weight
                    }
                )
            }
        }
    }

    fun updateUserData(
        unitSystem : String? = null,
        gender : String? = null,
        age : String? = null,
        height : Float? = null,
        weight : Float? = null,
    ) {
        viewModelScope.launch {
            _signUpData.update { data ->
                data.copy(
                    unitOfMeasurement = unitSystem ?: data.unitOfMeasurement,
                    age = age ?: data.age,
                    height = height ?: data.height,
                    weight = weight ?: data.weight,
                    gender = gender ?: data.gender,
                )
            }
        }
    }

    fun updateActivityData(
        activityLevel : String? = null,
    ) {
        viewModelScope.launch {
            _signUpData.update { data ->
                data.copy(
                    activityLevel = activityLevel
                )
            }
        }
    }

    fun updateUserProfile(
        firstName : String? = null,
        lastName : String? = null,
        userName : String? = null,
    ) {
        viewModelScope.launch {
            _signUpData.update { data ->
                data.copy(
                    firstName = firstName ?: data.firstName,
                    lastName = lastName ?: data.lastName,
                    userName = userName ?: data.userName,
                )
            }
        }
    }

    fun uploadUserData() {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val info = _signUpData.value

                repo.insertUserPreference(
                    UserPreferenceData(
                        firstName = info.firstName,
                        lastName = info.lastName,
                        userName = info.userName,
                        age = info.age,
                        gender = info.gender,
                        height = info.height,
                        weight = info.weight,
                        unitOfMeasurement = info.unitOfMeasurement,
                        dailyGoal = 1L // this needs to be calculated later
                    )
                )

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}