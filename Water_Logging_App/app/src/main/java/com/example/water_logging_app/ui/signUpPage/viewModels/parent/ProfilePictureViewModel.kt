package com.example.water_logging_app.ui.signUpPage.viewModels.parent

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.photoPicker.data.repository.PhotoRepositoryImpl
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.photoPicker.worker.di.PhotoWorkerSchedule
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfilePictureViewModel @Inject constructor(
    private val repository: PhotoRepositoryImpl,
    private val photoWorker: PhotoWorkerSchedule
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
            _profilePictureUri.update { data ->
                data.copy(
                    filePath = uri ?: _profilePictureUri.value.filePath
                )
            }
        }
    }

    fun uploadUserProfilePicture() {
        viewModelScope.launch {
            if(_profilePictureUri.value.filePath.isEmpty()) {
                return@launch
            }

            _profilePictureUri.update { data ->
                data.copy(
                    isLoading = true
                )
            }

            photoWorker.enqueueSaveUserPhoto(_profilePictureUri.value.filePath)

            _profilePictureUri.update { data ->
                data.copy(
                    isLoading = false
                )
            }
        }
    }
}