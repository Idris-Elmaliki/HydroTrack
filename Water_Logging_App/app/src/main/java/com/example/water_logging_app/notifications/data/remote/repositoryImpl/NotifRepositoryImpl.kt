package com.example.water_logging_app.notifications.data.remote.repositoryImpl

import com.example.water_logging_app.notifications.data.remote.api.NotificationApi
import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import com.example.water_logging_app.notifications.domain.remote.modelData.RegisterDeviceData
import com.example.water_logging_app.notifications.domain.remote.modelData.TestNotifRequestData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import javax.inject.Inject

class NotifRepositoryImpl @Inject constructor(
    private val notifApi : NotificationApi
) : NotifRepository {
    override suspend fun registerUserDevice(request: RegisterDeviceData) {
        TODO("Not yet implemented")
    }

    override suspend fun sendNotifRequest(notifRequest: NotifRequestData) {
        TODO("Not yet implemented")
    }

    override suspend fun testNotifRequest(testRequest: TestNotifRequestData) {
        TODO("Not yet implemented")
    }

    override suspend fun unregisterUserDevice(installationId: String) {
        TODO("Not yet implemented")
    }
}

private fun NotifRequestData.toNotifRequestDTO() {}

private fun RegisterDeviceData.toNotifRequestDTO() {}

private fun TestNotifRequestData.totestNotifDTO() {}