package com.example.water_logging_app.notifications.data.local

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.water_logging_app.time.TimeConversion
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

/*
* This is the class for notification DataStore
*
* We have 3 checks:
*   1.) If the user wants notifications
*   2.) If they do, what time will the notification be at?
*   3.) If they don't want to be reminded to set up notifications
*/
@Singleton
class NotificationDataStoreManager @Inject constructor(
    @param:ApplicationContext private val context : Context
) {
    private val Context.dataStore by preferencesDataStore("notifications")

    fun getAllowNotifications() : Flow<Boolean> {
        return context.dataStore.data.map { data ->
            data[allowNotifications] ?: true
        }
    }

    fun getDontShowNotificationSetUp() : Flow<Boolean> {
        return context.dataStore.data.map { data ->
            data[dontShowNotificationSetUp] ?: false
        }
    }

    fun getNotificationTime() : Flow<LocalTime?> {
        return context.dataStore.data.map { data ->
            val timeString = data[notificationTime]

            if (timeString != null) {
                TimeConversion.getLocalTimeFromStringD(timeString)
            }
            else {
                null
            }
        }
    }

    suspend fun setNotificationData(
        dontShowNotificationSetUp : Boolean?,
        allowNotifications : Boolean?,
        notificationTime : String?,
    ) {
        context.dataStore.edit { data ->
            data[Companion.dontShowNotificationSetUp] = dontShowNotificationSetUp ?: data[Companion.dontShowNotificationSetUp] ?: false
        }

        context.dataStore.edit { data ->
            data[Companion.allowNotifications] = allowNotifications ?: data[Companion.allowNotifications] ?: false
        }

        context.dataStore.edit { data ->
            data[Companion.notificationTime] = notificationTime ?: data[Companion.notificationTime] ?: "00:00"
        }
    }

    companion object {
        val allowNotifications = booleanPreferencesKey("allow_Notifications")
        val dontShowNotificationSetUp = booleanPreferencesKey("show_Notifications_Set_up")
        val notificationTime = stringPreferencesKey("notification_time")
    }
}