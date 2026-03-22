package com.example.water_logging_app.photoPicker.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.water_logging_app.photoPicker.domain.repository.PhotoRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : PhotoRepository {
    private val directory : File = File(context.filesDir, "image")

    override suspend fun saveImage(uri: Uri) {
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, "user_profile.jpg")

       withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
            }
            catch (e : Exception) {
                // we want the app to crash (for now)
                Log.e("PhotoRepositoryImpl", "saveImage error: ${e.message}")
                throw e
            }
       }
    }

    override suspend fun getImageUrl(): String? {
        val file = File(directory, "user_profile.jpg")

        return if (file.exists()) {
            file.absolutePath
        }
        else {
            null
        }
    }
}