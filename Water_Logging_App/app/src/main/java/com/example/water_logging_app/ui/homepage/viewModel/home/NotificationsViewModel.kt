package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.notifications.data.local.NotificationDataStoreManager
import com.example.water_logging_app.notifications.domain.NotificationSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val dataStore : NotificationDataStoreManager
) : ViewModel() {
    private var _notifState = MutableStateFlow(NotificationSettings())
    val notifState : StateFlow<NotificationSettings> = _notifState.asStateFlow()

    init {
        loadNotificationSettings()
    }

    fun loadNotificationSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                combine(
                    dataStore.getAllowNotifications(),
                    dataStore.getShowNotificationSetUp(),
                    dataStore.getNotificationTime()
                ) { param1, param2, param3 ->
                    NotificationSettings(
                        allowNotifications = param1,
                        dontShowNotificationSetUp = param2,
                        notificationTime = param3
                    )
                }
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    _notifState.update { data }
                }
            }
            catch (e : Exception) {
                _notifState.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }

    fun updateAllowNotifications(
        allowNotifications : Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _notifState.update { data ->
                data.copy(
                    allowNotifications = allowNotifications
                )
            }
        }
    }

    fun updateShowNotificationSetUp(
        showNotificationSetUp : Boolean,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _notifState.update { data ->
                data.copy(
                    dontShowNotificationSetUp = showNotificationSetUp
                )
            }
        }
    }

    fun updateNotificationTime(
        notificationTime : LocalTime
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _notifState.update { data ->
                data.copy(
                    notificationTime = notificationTime
                )
            }
        }
    }

    fun updateNotificationSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

            }
            catch (e : Exception) {

            }
        }
    }
}