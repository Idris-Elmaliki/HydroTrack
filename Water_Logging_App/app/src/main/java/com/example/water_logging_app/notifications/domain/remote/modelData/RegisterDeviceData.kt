package com.example.water_logging_app.notifications.domain.remote.modelData

data class RegisterDeviceData(
    val installationId: String,
    val fcmToken: String
)
