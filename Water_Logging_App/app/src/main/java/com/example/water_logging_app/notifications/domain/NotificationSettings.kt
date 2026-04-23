package com.example.water_logging_app.notifications.domain

import java.time.LocalTime

data class NotificationSettings(
    val error : String? = null,

    val allowNotifications : Boolean = false,
    val dontShowNotificationSetUp : Boolean = false,
    val notificationTime : LocalTime? = null
)
