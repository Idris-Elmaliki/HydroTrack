package com.example.water_logging_app.notifications.data.remote.di

import android.util.Log
import com.example.water_logging_app.notifications.data.remote.api.NotificationApi
import com.example.water_logging_app.notifications.data.remote.repositoryImpl.NotifRepositoryImpl
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
* Note: in order to run the backend locally, write command: dotnet run --project NotificationSystem
*/
@Module
@InstallIn(SingletonComponent::class)
object NotifModel {
    @Singleton
    @Provides
    fun getNotifApi() : NotificationApi {
        // NOTE: Port 5000 is used by macOS AirPlay Receiver, which may cause HTTP 403 Forbidden.
        // If you are on macOS Monterey or later, use a different port (e.g., 5001)
        // or disable 'AirPlay Receiver' in System Settings -> General -> AirPlay & Handoff.

        val baseUrl = "http://10.0.2.2:5001/" // this the ip address for emulators

        // this is for testing whether we connected to the ports correctly
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(
                    request.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build()
                )

                Log.d("NotifApi", "Request: ${request.method} ${request.url}")
                Log.d("NotifApi", "Response: ${response.code} ${response.message}")

                response
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
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