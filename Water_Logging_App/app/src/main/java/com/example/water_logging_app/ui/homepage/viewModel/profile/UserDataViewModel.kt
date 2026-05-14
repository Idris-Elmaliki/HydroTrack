package com.example.water_logging_app.ui.homepage.viewModel.profile

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

    private var _editedUserData = MutableStateFlow(UserPreferenceData())
    val editedUserData : StateFlow<UserPreferenceData> = _editedUserData.asStateFlow()

    private var _profilePictureUri = MutableStateFlow(PhotoData())
    val profilePicture : StateFlow<PhotoData> = _profilePictureUri.asStateFlow()

    init {
        loadUserData()
        loadProfilePic()
    }

    private fun loadUserData() {
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

                _editedUserData.update { data ->
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

    fun uploadNewUpdatedData() {
        Log.d("Profile", "Entered uploadUpdatedData")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newData = _editedUserData.value

                _userData.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                Log.d("Profile", "Before Saved data: ${_userData.value}")
                repo.insertUserPreference(newData)
                loadUserData()
                Log.d("Profile", "After Saved data: ${_userData.value}")

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

    fun uploadNewUpdatedFilePath(
        newData: PhotoData
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                photoRepo.saveImage(newData.filePath.toUri())
                loadProfilePic()

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

    fun resetEditUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val originalUserData = _userData.value

            _editedUserData.update { data ->
                data.copy(
                    firstName = originalUserData.firstName,
                    lastName = originalUserData.lastName,
                    userName = originalUserData.userName,
                    age = originalUserData.age,
                    gender = originalUserData.gender,
                    height = originalUserData.height,
                    weight = originalUserData.weight,
                    dailyGoal = originalUserData.dailyGoal,
                    unitOfMeasurement = originalUserData.unitOfMeasurement
                )
            }
        }
    }

    fun updateEditedUserData(
        firstName: String? = null,
        lastName: String? = null,
        userName: String? = null,
        age: String? = null,
        gender: String? = null,
        height: Float? = null,
        weight: Float? = null,
        dailyGoal: Long? = null,
        unitOfMeasurement: String? = null
    ) {
        _editedUserData.update { data ->
            data.copy(
                firstName = firstName ?: data.firstName,
                lastName = lastName ?: data.lastName,
                userName = userName ?: data.userName,
                age = age ?: data.age,
                gender = gender ?: data.gender,
                height = height ?: data.height,
                weight = weight ?: data.weight,
                dailyGoal = dailyGoal ?: data.dailyGoal,
                unitOfMeasurement = unitOfMeasurement ?: data.unitOfMeasurement
            )
        }
    }
}