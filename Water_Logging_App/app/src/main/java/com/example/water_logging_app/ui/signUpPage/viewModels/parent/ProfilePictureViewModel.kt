package com.example.water_logging_app.ui.signUpPage.viewModels.parent

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.photoPicker.data.repository.PhotoRepositoryImpl
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfilePictureViewModel @Inject constructor(
    private val repository: PhotoRepositoryImpl
) : ViewModel() {
    private val _profilePictureUri = MutableStateFlow(PhotoData())
    val profilePictureUri: StateFlow<PhotoData> = _profilePictureUri.asStateFlow()

    init {
        getProfilePicture()
    }
    private fun getProfilePicture() {
        viewModelScope.launch {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        filePath = repository.getImageUrl() ?: "",
                        isLoading = false
                    )
                }
            }
            catch (e: Exception) {
                _profilePictureUri.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    // I want to save the image data to the directory later (during the second loading screen)
    fun updateProfilePicturePath(uri: String?) {
        viewModelScope.launch {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        filePath = uri ?: _profilePictureUri.value.filePath
                    )
                }
            }
            catch (e: Exception) {
                _profilePictureUri.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun saveProfilePicture() {
        viewModelScope.launch {
            try {
                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                repository.saveImage(
                    _profilePictureUri.value.filePath.toUri()
                )

                _profilePictureUri.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _profilePictureUri.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}