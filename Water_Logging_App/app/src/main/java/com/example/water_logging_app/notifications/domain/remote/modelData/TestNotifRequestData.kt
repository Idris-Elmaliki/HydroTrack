package com.example.water_logging_app.notifications.domain.remote.modelData

data class TestNotifRequestData(
    val fcmToken: String,
    val title: String,
    val body: String
)
