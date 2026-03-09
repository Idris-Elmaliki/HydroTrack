package com.example.water_logging_app.photoPicker.domain

data class PhotoData(
    val isLoading : Boolean = false,
    val error : String? = null,

    val uri : String = ""
)
