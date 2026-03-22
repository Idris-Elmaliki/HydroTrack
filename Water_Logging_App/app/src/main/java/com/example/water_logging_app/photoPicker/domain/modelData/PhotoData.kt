package com.example.water_logging_app.photoPicker.domain.modelData

data class PhotoData(
    val isLoading : Boolean = false,
    val error : String? = null,

    val filePath : String = ""
)
