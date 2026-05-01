package com.example.water_logging_app.notifications.domain.remote.repository

import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import com.example.water_logging_app.notifications.domain.remote.modelData.RegisterDeviceData
import com.example.water_logging_app.notifications.domain.remote.modelData.TestNotifRequestData

interface NotifRepository {
    suspend fun registerUserDevice(request : RegisterDeviceData)

    suspend fun unregisterUserDevice(installationId : String)

    suspend fun testNotifRequest(testRequest : TestNotifRequestData)

    suspend fun sendNotifRequest(notifRequest : NotifRequestData)
}