package com.example.water_logging_app.notifications.data.remote.dto

// Sent to verify Firebase credentials and FCM delivery are working using a raw token
data class TestNotifRequestDTO(
    val deviceToken: String,
    val title: String,
    val body: String
)
