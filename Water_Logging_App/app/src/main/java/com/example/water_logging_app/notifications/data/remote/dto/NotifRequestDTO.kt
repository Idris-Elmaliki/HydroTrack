package com.example.water_logging_app.notifications.data.remote.dto

// Sent to trigger a push notification to a specific installation using its stored token
data class NotifRequestDTO(
    val installationId: String,
    val title: String,
    val body: String
)