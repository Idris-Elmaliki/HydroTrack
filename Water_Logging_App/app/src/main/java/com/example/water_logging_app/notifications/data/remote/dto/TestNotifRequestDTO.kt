package com.example.water_logging_app.notifications.data.remote.dto

import com.google.gson.annotations.SerializedName

// Sent to verify Firebase credentials and FCM delivery are working using a raw token
data class TestNotifRequestDTO(
    @SerializedName("DeviceToken")
    val deviceToken: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Body")
    val body: String
)