package com.example.water_logging_app.notifications.data.remote.instance

import com.example.water_logging_app.notifications.data.remote.api.NotificationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotifInstance {
    // Forgot to comment, as of now I am running the backend locally for testing purposes
    private const val BASE_URL = "http://10.0.2.2:5000/"

    val notifApi : NotificationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationApi::class.java)
    }
}