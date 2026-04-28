package com.example.water_logging_app.ui.homepage.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.notifications.data.local.NotificationDataStoreManager
import com.example.water_logging_app.notifications.domain.NotificationSettings
import com.example.water_logging_app.time.TimeConversion
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
                // man I hate flows
                combine(
                    dataStore.getDontShowNotificationSetUp(),
                    dataStore.getAllowNotifications(),
                    dataStore.getNotificationTime()
                ) { setUpNotif, allowNotif, notifTime ->
                    NotificationSettings(
                        dontShowNotificationSetUp = setUpNotif,
                        allowNotifications = allowNotif,
                        notificationTime = notifTime
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

    fun uploadNotificationSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _notifState.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val vmData = _notifState.value

                dataStore.setNotificationData(
                    // what if the user doesn't check the box but did set up notif?
                    dontShowNotificationSetUp =
                        if(!vmData.allowNotifications) { vmData.dontShowNotificationSetUp }
                            else { true },
                    allowNotifications = vmData.allowNotifications,
                    // the notification time can be null (we need this check since we are sending everything in bulk)
                    notificationTime =
                        if(vmData.notificationTime != null) { TimeConversion.getStringFromLocalTimeD(vmData.notificationTime) }
                            else { null }
                )
            }
            catch (e : Exception) {
                _notifState.update { data ->
                    data.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}