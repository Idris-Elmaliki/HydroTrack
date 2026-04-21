package com.example.water_logging_app.ui.homepage.viewModel.settings

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

    fun loadSettingsData() {
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

    fun loadProfilePic() {
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
}