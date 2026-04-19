package com.example.water_logging_app.notifications.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.water_logging_app.time.TimeConversion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalTime

class NotificationDataStoreManager(
    private val context : Context
) {
    val Context.dataStore by preferencesDataStore("notifications")

    suspend fun setAllowNotifications(allowNotifications : Boolean) {
        context.dataStore.edit { data ->
            data[Companion.allowNotifications] = allowNotifications
        }
    }

    fun getAllowNotifications() : Flow<Boolean> {
        return context.dataStore.data.map { data ->
            data[allowNotifications] ?: false
        }
    }

    suspend fun setShowNotificationSetUp(showNotificationSetUp : Boolean) {
        context.dataStore.edit { data ->
            data[Companion.showNotificationSetUp] = showNotificationSetUp
        }
    }

    fun getShowNotificationSetUp() : Flow<Boolean> {
        return context.dataStore.data.map { data ->
            data[showNotificationSetUp] ?: true
        }
    }

    suspend fun setNotificationTime(notificationTime : LocalTime) {
        val notificationTimeStr = TimeConversion.getStringFromLocalTimeD(notificationTime)

        context.dataStore.edit { data ->
            data[Companion.notificationTime] = notificationTimeStr
        }
    }

    fun getNotificationTime() : Flow<LocalTime> {
        val notificationTimeFlow : String = context.dataStore.data.map { data ->
            data[notificationTime] ?: "00:00"
        }.toString()

        return TimeConversion.getLocalTimeFromStringD(notificationTimeFlow)
    }

    companion object {
        val allowNotifications = booleanPreferencesKey("allow_Notifications")
        val showNotificationSetUp = booleanPreferencesKey("show_Notifications_Set_up")
        val notificationTime = stringPreferencesKey("notification_time")
    }
}