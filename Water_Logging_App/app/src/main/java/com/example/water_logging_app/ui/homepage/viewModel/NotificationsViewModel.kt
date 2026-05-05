package com.example.water_logging_app.ui.homepage.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water_logging_app.fcm.worker.di.NotificationWorkerSchedule
import com.example.water_logging_app.notifications.data.local.NotificationDataStoreManager
import com.example.water_logging_app.notifications.domain.local.NotificationSettings
import com.example.water_logging_app.notifications.domain.remote.modelData.NotifRequestData
import com.example.water_logging_app.notifications.domain.remote.modelData.RegisterDeviceData
import com.example.water_logging_app.notifications.domain.remote.modelData.TestNotifRequestData
import com.example.water_logging_app.notifications.domain.remote.repository.NotifRepository
import com.example.water_logging_app.time.TimeConversion
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val dataStore : NotificationDataStoreManager,
    private val notifWorker : NotificationWorkerSchedule,
    private val repo : NotifRepository,
    @param:ApplicationContext private val context : Context
) : ViewModel() {
    private var _notifState = MutableStateFlow(NotificationSettings())
    val notifState : StateFlow<NotificationSettings> = _notifState.asStateFlow()

    init {
        registerDevice()
        loadNotificationSettings()
    }
    @SuppressLint("HardwareIds")
    private fun registerDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _notifState.update { data ->
                    data.copy(
                        isLoading = true
                    )
                }

                val fcmToken = FirebaseMessaging.getInstance().token.await() // this gives us the fcm key
                val installationId = Settings.Secure.getString(
                    context.contentResolver,
                    ANDROID_ID
                ) // this gives us the installation ID

                repo.registerUserDevice(
                    RegisterDeviceData(
                        installationId = installationId,
                        fcmToken = fcmToken
                    )
                )

                _notifState.update { data ->
                    data.copy(
                        isLoading = false
                    )
                }
            }
            catch (e : Exception) {
                Log.e("TestWorker", "registering device error: ${e.message}", e)

                _notifState.update { data ->
                    data.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
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

    @SuppressLint("HardwareIds")
    fun uploadNotificationSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val vmData = _notifState.value

                dataStore.setNotificationData(
                    // what if the user doesn't check the box but did set up notif?
                    dontShowNotificationSetUp =
                        if(!vmData.allowNotifications || vmData.notificationTime == null) { vmData.dontShowNotificationSetUp }
                            else { true },
                    allowNotifications = vmData.allowNotifications,
                    // the notification time can be null (we need this check since we are sending everything in bulk)
                    notificationTime =
                        if(vmData.notificationTime != null) { TimeConversion.getStringFromLocalTimeD(vmData.notificationTime) }
                            else { null }
                )

                if(vmData.allowNotifications && vmData.notificationTime != null) {
                    val fcmToken = FirebaseMessaging.getInstance().token.await() // this gives us the fcm key
                    val installationId = Settings.Secure.getString(context.contentResolver, ANDROID_ID) // this gives us the installation ID

                    repo.registerUserDevice(
                        RegisterDeviceData(
                            installationId = installationId,
                            fcmToken = fcmToken
                        )
                    )

                    val now = LocalTime.now()
                    val delay = if(vmData.notificationTime.isAfter(now)) {
                        Duration.between(now, vmData.notificationTime)
                    }
                    else {
                        Duration.between(now, vmData.notificationTime).plusDays(1)
                    }

                    notifWorker.enqueueNotificationData(
                        notifData = NotifRequestData(
                            installationId = installationId,
                            title = "Hydration Reminder",
                            body = "It's time to log your water intake and stay hydrated!"
                        ),
                        initialDelay = delay.toMillis()
                    )

                    notifWorker.enqueueTestNotification(
                        notifData = TestNotifRequestData(
                            fcmToken = fcmToken,
                            title = "Hydration Testing",
                            body = "Hello sir"
                        )
                    )
                }
            }
            catch (e : Exception) {
                Log.e("TestWorker", "uploadNotificationSettings error: ${e.message}", e)
                _notifState.update { data ->
                    data.copy(
                        error = e.message
                    )
                }
            }
        }
    }
}