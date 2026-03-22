package com.example.water_logging_app.photoPicker.data.di

import android.content.Context
import com.example.water_logging_app.photoPicker.data.repository.PhotoRepositoryImpl
import com.example.water_logging_app.photoPicker.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {
    @Provides
    @Singleton
    fun providePhotoRepository(
        @ApplicationContext context: Context
    ): PhotoRepository {
        return PhotoRepositoryImpl(
            context = context,
        )
    }

}