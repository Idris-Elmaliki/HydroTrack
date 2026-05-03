package com.example.water_logging_app.notifications.data.remote.dto

import com.google.gson.annotations.SerializedName

// Sent to trigger a push notification to a specific installation using its stored token
data class NotifRequestDTO(
    @SerializedName("InstallationId")
    val installationId: String,

    @SerializedName("Title")
    val title: String,

    @SerializedName("Body")
    val body: String
)