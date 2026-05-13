package com.example.water_logging_app.ui.homepage.viewModel.settings

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.photoPicker.data.repository.PhotoRepositoryImpl
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.data.repository.UserPreferenceRepositoryImpl
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val repo : UserPreferenceRepositoryImpl,
    private val photoRepo : PhotoRepositoryImpl
) : ViewModel() {
    private var _userData = MutableStateFlow(UserPreferenceData())
    val userData : StateFlow<UserPreferenceData> = _userData.asStateFlow()

    private var _profilePictureUri = MutableStateFlow(PhotoData())
    val profilePicture : StateFlow<PhotoData> = _profilePictureUri.asStateFlow()

    init {
        loadSettingsData()
        loadProfilePic()
    }

    private fun loadSettingsData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userData = repo.getUserPreference()?: UserPreferenceData()

                _userData.update { data ->
                    data.copy(
                        firstName = userData.firstName,
                        lastName = userData.lastName,
                        userName = userData.userName,
                        age = userData.age,
                        gender = userData.gender,
                        height = userData.height,
                        weight = userData.weight,
                        dailyGoal = userData.dailyGoal,
                        unitOfMeasurement = userData.unitOfMeasurement
                    )
                }

            } catch (e: Exception) {
                _userData.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    private fun loadProfilePic() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val uri = photoRepo.getImageUrl()

                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = false,
                        filePath = uri.toString()
                    )
                }
            }
            catch (e : Exception) {
                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun uploadUpdatedData() {
        Log.d("Profile", "Entered uploadUpdatedData")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _userData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repo.insertUserPreference(_userData.value)

                _userData.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                Log.e("UserDataVM", e.message, e)
                _userData.update { data ->
                    data.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onUserDataChange(newData: UserPreferenceData) {
        Log.d("Profile", "Entered onUserDataChange")
        viewModelScope.launch(Dispatchers.IO) {
            _userData.update { data ->
                Log.d("Profile", "Entered onUserDataChange update")
                data.copy(
                    firstName = newData.firstName,
                    lastName = newData.lastName,
                    userName = newData.userName,
                    age = newData.age,
                    gender = newData.gender,
                    height = newData.height,
                    weight = newData.weight,
                    dailyGoal = newData.dailyGoal,
                    unitOfMeasurement = newData.unitOfMeasurement
                )
            }
            Log.d("Profile", "Entered ${_userData.value.firstName}")
        }
    }

    fun onFilePathChange(newData: PhotoData) {
        viewModelScope.launch(Dispatchers.IO) {
            _profilePictureUri.update { data ->
                data.copy(
                    filePath = newData.filePath
                )
            }
        }
    }

    fun uploadUpdatedFilePath() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                photoRepo.saveImage(_profilePictureUri.value.filePath.toUri())

                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                Log.e("UserDataVM", e.message, e)
                _profilePictureUri.update { data ->
                    data.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}