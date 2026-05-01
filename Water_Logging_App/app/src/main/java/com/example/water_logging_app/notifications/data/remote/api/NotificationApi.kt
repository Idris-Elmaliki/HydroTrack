package com.example.water_logging_app.notifications.data.remote.api

import com.example.water_logging_app.notifications.data.remote.dto.NotifRequestDTO
import com.example.water_logging_app.notifications.data.remote.dto.RegisterDeviceDTO
import com.example.water_logging_app.notifications.data.remote.dto.TestNotifRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApi {
    @POST("/devices/register")
    suspend fun registerDevice(@Body request : RegisterDeviceDTO)

    @DELETE("/devices/{installationId}")
    suspend fun deleteDevice(@Path("installationId") installationId : String)

    @POST("/notifications/test")
    suspend fun testNotificationRequest(@Body testRequest : TestNotifRequestDTO)

    @POST("/notifications/send-to-installation")
    suspend fun sendNotificationRequest(@Body notifRequest : NotifRequestDTO)
}