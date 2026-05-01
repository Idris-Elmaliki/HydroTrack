package com.example.water_logging_app.ui.homepage.viewModel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.notifications.data.local.NotificationDataStoreManager
import com.example.water_logging_app.notifications.domain.local.NotificationSettings
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
                Log.d("Datastore", "Inside fun loadNotificationSettings()")

                Log.d("Datastore", "_notifState.value.dontShowNotifSetUp: ${_notifState.value.dontShowNotificationSetUp}")
                Log.d("Datastore", "_notifState.value.allowNotif: ${_notifState.value.allowNotifications}")
                Log.d("Datastore", "_notifState.value.notifTime: ${_notifState.value.notificationTime}")

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
                    _notifState.update {
                        data.copy(
                            isLoading = false
                        )
                    }

                    Log.d("Datastore", "_notifState.value.dontShowNotifSetUp: ${_notifState.value.dontShowNotificationSetUp}")
                    Log.d("Datastore", "_notifState.value.allowNotif: ${_notifState.value.allowNotifications}")
                    Log.d("Datastore", "_notifState.value.notifTime: ${_notifState.value.notificationTime}")
                }
            }
            catch (e : Exception) {
                Log.d("Datastore", "Inside exception of fun loadNotificationSettings()")
                Log.e("Datastore", "loadNotificationSettings error: ${e.message}", e)
                _notifState.update { data ->
                    data.copy(
                        isLoading = false,
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
            Log.d("Datastore", "before! _notifState.value.allowNotifications: ${_notifState.value.allowNotifications}")

            _notifState.update { data ->
                data.copy(
                    allowNotifications = allowNotifications
                )
            }

            Log.d("Datastore", "after! _notifState.value.allowNotifications: ${_notifState.value.allowNotifications}")
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

                _notifState.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                Log.e("Datastore", "uploadNotificationSettings error: ${e.message}", e)
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