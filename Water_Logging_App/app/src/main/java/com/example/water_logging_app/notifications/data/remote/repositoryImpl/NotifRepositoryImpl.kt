package com.example.water_logging_app.notifications.data.remote.repositoryImpl

import com.example.water_logging_app.notifications.data.remote.api.NotificationApi
import com.example.water_logging_app.notifications.data.remote.dto.NotifRequestDTO
import com.example.water_logging_app.notifications.data.remote.dto.RegisterDeviceDTO
import com.example.water_logging_app.notifications.data.remote.dto.TestNotifRequestDTO
import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import com.example.water_logging_app.notifications.domain.remote.modelData.RegisterDeviceData
import com.example.water_logging_app.notifications.domain.remote.modelData.TestNotifRequestData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import javax.inject.Inject

class NotifRepositoryImpl @Inject constructor(
    private val notifApi : NotificationApi
) : NotifRepository {
    override suspend fun registerUserDevice(request: RegisterDeviceData) {
        notifApi.registerDevice(
            request = request.toRegisterDeviceDTO()
        )
    }

    override suspend fun unregisterUserDevice(installationId: String) {
        notifApi.deleteDevice(
            installationId = installationId
        )
    }

    override suspend fun testNotifRequest(testRequest: TestNotifRequestData) {
        notifApi.testNotificationRequest(
            testRequest = testRequest.totestNotifDTO()
        )
    }

    override suspend fun sendNotifRequest(notifRequest: NotifRequestData) {
        notifApi.sendNotificationRequest(
            notifRequest = notifRequest.toNotifRequestDTO()
        )
    }
}

private fun NotifRequestData.toNotifRequestDTO() : NotifRequestDTO {
    return NotifRequestDTO(
        installationId = installationId,
        title = title,
        body = body
    )
}

private fun RegisterDeviceData.toRegisterDeviceDTO() : RegisterDeviceDTO {
    return RegisterDeviceDTO(
        installationId = installationId,
        FcmToken = fcmToken
    )
}

private fun TestNotifRequestData.totestNotifDTO() : TestNotifRequestDTO {
    return TestNotifRequestDTO(
        deviceToken = fcmToken,
        title = title,
        body = body
    )
}