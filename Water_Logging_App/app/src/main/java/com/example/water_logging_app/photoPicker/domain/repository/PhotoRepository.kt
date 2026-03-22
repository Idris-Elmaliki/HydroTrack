package com.example.water_logging_app.photoPicker.domain.repository

import android.net.Uri

interface PhotoRepository {
    suspend fun saveImage(uri: Uri) // we grab the uri of the image

    suspend fun getImageUrl(): String? // this returns us a file path or null
}