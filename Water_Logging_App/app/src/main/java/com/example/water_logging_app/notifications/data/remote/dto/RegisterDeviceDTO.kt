package com.example.water_logging_app.notifications.data.remote.dto

// Sent to the backend to register or update a device's FCM token for a given installation
data class RegisterDeviceDTO(
    val installationId: String,
    val fcmToken: String
)
