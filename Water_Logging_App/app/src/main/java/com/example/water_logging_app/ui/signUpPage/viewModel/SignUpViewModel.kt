package com.example.water_logging_app.ui.signUpPage.viewModel

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

    fun updateUserProfile(
        firstName : String? = null,
        lastName : String? = null,
        userName : String? = null,
        gender : String? = null,
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        firstName = firstName ?: data.firstName,
                        lastName = lastName ?: data.lastName,
                        userName = userName ?: data.userName,
                        gender = gender ?: data.gender
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

    fun updateUserData(
        age : Int? = null,
        height : Float? = null,
        weight : Float? = null,
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,
                        age = age ?: data.age,
                        height = height ?: data.height,
                        weight = weight ?: data.weight
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

    fun updateUsersPreferences(
        dailyGoal : Long? = null,
        isMetric : Boolean? = null,
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        dailyGoal = dailyGoal ?: data.dailyGoal,
                        isMetric = isMetric ?: data.isMetric
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

    fun checkIfDataIsComplete1() : List<String> { // for screen 1 (UserNameScreen)
        val errorList = mutableListOf<String>()
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                when {
                    _signUpData.value.firstName.isBlank() -> {
                        errorList.add("First Name is Empty.")
                    }
                    _signUpData.value.lastName.isBlank() -> {
                        errorList.add("Last Name is Empty.")
                    }
                    _signUpData.value.userName.isBlank() -> {
                        errorList.add("User Name is Empty.")
                    }
                    _signUpData.value.gender == null -> {
                        errorList.add("Gender is Empty.")
                    }
                }

                if(errorList.isNotEmpty()) {
                    throw Exception("Missing Data Found")
                }
                else {
                    _signUpData.update { data ->
                        data.copy(
                            isLoading = false,
                            error = null
                        )
                    }
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

        return errorList
    }

    // we might still need this function in the future (I'm too lazy to rewrite it...)
/*
    fun addUserData(
        userData : UserPreferenceData
    ) {
        viewModelScope.launch {
            try {
                _signUpData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.insertUserPreference(
                    UserPreferenceData(
                        firstName = userData.firstName,
                        lastName = userData.lastName,
                        userName = userData.userName,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        isMetric = userData.isMetric,
                    )
                )

                _signUpData.update { data ->
                    data.copy(
                        isLoading = false,

                        firstName = userData.firstName,
                        lastName = userData.lastName,
                        userName = userData.userName,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        isMetric = userData.isMetric,
                    )
                }
            } catch (e: Exception) {
                _signUpData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
 */
}