package com.example.water_logging_app.notifications.data.remote.dto

import com.google.gson.annotations.SerializedName

// Sent to the backend to register or update a device's FCM token for a given installation
data class RegisterDeviceDTO(
    @SerializedName("InstallationId")
    val installationId : String,

    @SerializedName("FcmToken")
    val FcmToken : String
)

