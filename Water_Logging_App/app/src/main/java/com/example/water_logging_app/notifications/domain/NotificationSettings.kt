package com.example.water_logging_app.notifications.domain

import java.time.LocalTime

data class NotificationSettings(
    val error : String? = null,
    val isLoading : Boolean = false,

    val allowNotifications : Boolean = false,
    val dontShowNotificationSetUp : Boolean = false,
    val notificationTime : LocalTime? = null
)
