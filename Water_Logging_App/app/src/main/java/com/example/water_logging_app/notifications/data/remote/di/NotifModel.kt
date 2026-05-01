package com.example.water_logging_app.notifications.data.remote.di

import com.example.water_logging_app.notifications.data.remote.api.NotificationApi
import com.example.water_logging_app.notifications.data.remote.repositoryImpl.NotifRepositoryImpl
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotifModel {
    @Singleton
    @Provides
    fun getNotifApi() : NotificationApi {
        val baseUrl = "http://10.0.2.2:5000/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationApi::class.java)

    }

    @Singleton
    @Provides
    fun getNotifRepository(
        notifApi : NotificationApi
    ) : NotifRepository {
        return NotifRepositoryImpl(notifApi = notifApi)
    }
}