package com.example.water_logging_app.ui.homepage

import android.Manifest
import android.os.Build
import android.text.format.DateFormat
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.R
import com.example.water_logging_app.notifications.domain.NotificationSettings
import com.example.water_logging_app.ui._navigation.navData.homepage.BottomNavList
import com.example.water_logging_app.ui._navigation.navGraphs.homeGraph
import com.example.water_logging_app.ui.homepage.viewModel.home.NotificationsViewModel
import com.example.water_logging_app.ui.subscreens.PaginationSystemUi
import com.example.water_logging_app.ui.subscreens.alerts.ConfirmationAlertDialog
import com.example.water_logging_app.ui.theme.Aquamarine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUiLayout(
    modifier : Modifier,
    notifVM : NotificationsViewModel
) {
    val bottomNavController : NavHostController = rememberNavController()

    val notifData by notifVM.notifState.collectAsStateWithLifecycle()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    var showNotificationSetupPages by rememberSaveable { mutableStateOf(!notifData.dontShowNotificationSetUp) }
    var confirmUserInput by rememberSaveable { mutableStateOf(false) }

    val numList : List<Int> = when {
        notifData.allowNotifications && notifData.notificationTime == null -> {
            listOf(1, 2)
        }
        else -> {
            listOf(1, 2, 3, 4)
        }
    }

    val notifPageState = rememberPagerState { numList.size }

    LaunchedEffect(showNotificationSetupPages) {
        if(!showNotificationSetupPages) {
            notifVM.uploadNotificationSettings()
        }
    }

    Log.d("HomePage", "showNotificationSetupPages = $showNotificationSetupPages")
    if(showNotificationSetupPages) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(dimensionResource(R.dimen.container_padding)),
                    navigationIcon = {
                        if (notifPageState.currentPage >= 1) {
                            Row(
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            showNotificationSetupPages = false
                                        }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(end = dimensionResource(R.dimen.mini_text_padding))
                                        .size(dimensionResource(R.dimen.NavIconSize)),
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = null,
                                    tint = Aquamarine
                                )
                                Text(
                                    text = stringResource(R.string.Cancel),
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        lineHeight = 0.sp
                                    ),
                                    color = Aquamarine
                                )
                            }
                        }
                    },
                    title = {}
                )
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(dimensionResource(R.dimen.container_padding)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PaginationSystemUi(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = dimensionResource(R.dimen.container_padding),
                                bottom = dimensionResource(R.dimen.container_padding)
                            ),
                        allowScrolling = false,
                        pagerState = notifPageState,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var isChecked by rememberSaveable { mutableStateOf(false) }
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { data ->
                                notifVM.updateShowNotificationSetUp(
                                    showNotificationSetUp = data
                                )
                                isChecked = !isChecked
                            },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = Aquamarine,
                                checkedColor = Aquamarine,
                            )
                        )
                        Text(
                            text = stringResource(R.string.Dont_show_notif_setup_again),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            }
        ) { innerpadding ->
            val coroutineScope = rememberCoroutineScope()

            HorizontalPager(
                state = notifPageState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding)
                    .padding(dimensionResource(R.dimen.container_padding)),
                userScrollEnabled = false // Keep control via buttons/logic
            ) { page ->
                when (page) {
                    0 -> {
                        NotificationSetUpTransitionUi(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            coroutineScope.launch {
                                notifPageState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                    1 -> {
                        if(notifPageState.pageCount == 2) {
                            NotificationTimeSetUpUi(
                                modifier = Modifier.fillMaxSize(),
                                notifVM = notifVM,
                                notifData = notifData,
                                onCardClick = {
                                    confirmUserInput = !confirmUserInput
                                }
                            )
                        }
                        else {
                            TellUserAboutNotifPopUpUi {
                                coroutineScope.launch {
                                    notifPageState.animateScrollToPage(page + 1)
                                }
                            }
                        }
                    }
                    2 -> {
                        NotifLauncherUi(
                            notifData = notifData,
                            notifVM = notifVM,
                            onLauncherResult = {
                                coroutineScope.launch {
                                    notifPageState.animateScrollToPage(page + 1)
                                }
                            }
                        )
                    }
                    3 -> {
                        NotificationTimeSetUpUi(
                            modifier = Modifier.fillMaxSize(),
                            notifVM = notifVM,
                            notifData = notifData,
                            onCardClick = {
                                confirmUserInput = !confirmUserInput
                            }
                        )
                    }
                }
            }
        }
    }
    else {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BottomNavList.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (selectedItem == index) {
                                        item.selectedIcon
                                    } else {
                                        item.unselectedIcon
                                    },
                                    contentDescription = null
                                )
                            },
                            selected = (selectedItem == index),
                            onClick = {
                                selectedItem = index
                                bottomNavController.navigate(item.navHostName)
                            },
                            alwaysShowLabel = false
                        )
                    }
                }

            }
        ) { innerpadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = "home_graph",
                modifier = Modifier
                    .padding(innerpadding)
            ) {
                homeGraph(
                    navController = bottomNavController,
                    modifier = modifier
                )
            }
        }
    }

    if(confirmUserInput && notifData.dontShowNotificationSetUp) {
        ConfirmationAlertDialog(
            onDismiss = {
                confirmUserInput = !confirmUserInput
            },
            onContinuation = {
                showNotificationSetupPages = !showNotificationSetupPages
            }
        )
    }
}

@Composable
private fun NotificationSetUpTransitionUi(
    modifier : Modifier,
    onCardClick : () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.extra_container_padding)),
            text = stringResource(R.string.LetsFinishSetUp).trim(),
            style = MaterialTheme.typography.titleSmall
        )
        Card(
            modifier = Modifier
                .height(dimensionResource(R.dimen.ClickableCardHeight))
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    end = dimensionResource(R.dimen.container_padding)
                )
                .fillMaxWidth(),
            onClick = onCardClick,
            colors = CardDefaults.cardColors(
                containerColor = Aquamarine,
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Continue),
                    style = MaterialTheme.typography.labelMedium.copy(
                        lineHeight = 0.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun TellUserAboutNotifPopUpUi(
    onCardClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
           modifier = Modifier
               .size(dimensionResource(R.dimen.UiBoxSize))
               .background(
                   color = Aquamarine,
                   shape = CircleShape
               )
               .alpha(0.7f)
               .padding(dimensionResource(R.dimen.container_padding)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .size(dimensionResource(R.dimen.UiIconSize)),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
            )
        }

        Spacer(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.extra_container_padding))
        )

        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding)),
            text = stringResource(R.string.StayInTheLoop),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding)),
            text = stringResource(R.string.TellUserAboutNotif),
            style = MaterialTheme.typography.labelMedium.copy(
                textAlign = TextAlign.Center,

            )
        )

        Spacer(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.extra_container_padding))
        )

        Card(
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    end = dimensionResource(R.dimen.container_padding)
                )
                .height(dimensionResource(R.dimen.ClickableCardHeight))
                .shadow(
                    elevation = dimensionResource(R.dimen.card_shadow_elevation),
                    clip = true,
                    spotColor = Aquamarine,
                    ambientColor = Aquamarine,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(),
            onClick = {
                onCardClick()
            },
            colors = CardDefaults.cardColors(
                containerColor = Aquamarine,
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Continue),
                    style = MaterialTheme.typography.labelMedium.copy(
                        lineHeight = 0.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun NotifLauncherUi(
    notifVM: NotificationsViewModel,
    notifData : NotificationSettings,
    onLauncherResult: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> onLauncherResult() }

    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.UiBoxSize))
                .background(
                    color = Aquamarine,
                    shape = CircleShape
                )
                .alpha(0.7f)
                .padding(dimensionResource(R.dimen.container_padding)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .size(dimensionResource(R.dimen.UiIconSize)),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
            )
        }

        Spacer(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.extra_container_padding))
        )

        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding)),
            text = stringResource(R.string.AllowNotif),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            modifier = Modifier
                .padding(bottom = dimensionResource(R.dimen.container_padding)),
            text = stringResource(R.string.NotifBenefits),
            style = MaterialTheme.typography.labelMedium.copy(
                textAlign = TextAlign.Center,
            )
        )

        Card(
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    end = dimensionResource(R.dimen.container_padding)
                )
                .height(dimensionResource(R.dimen.ClickableCardHeight))
                .shadow(
                    elevation = dimensionResource(R.dimen.card_shadow_elevation),
                    clip = true,
                    spotColor = Aquamarine,
                    ambientColor = Aquamarine,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(),
            onClick = {
                if(Build.VERSION.SDK_INT >= 33) {
                    coroutineScope.launch {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
                else {
                    onLauncherResult()
                }
            },
            colors = CardDefaults.cardColors(
                containerColor = Aquamarine,
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.Allow),
                    style = MaterialTheme.typography.labelMedium.copy(
                        lineHeight = 0.sp
                    )
                )
            }
        }

        if(Build.VERSION.SDK_INT < 33) {
            Text(
                text = "Looks like your phone doesn't support the Notif Intent!"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationTimeSetUpUi(
    modifier : Modifier,
    notifVM: NotificationsViewModel,
    notifData : NotificationSettings,
    onCardClick: () -> Unit
) {
    val timeState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = DateFormat.is24HourFormat(LocalContext.current),
    )

    LaunchedEffect(timeState) {
        notifVM.updateNotificationTime(
            LocalTime.parse("${timeState.hour}:${timeState.minute}")
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.mini_text_padding)
                ),
            text = stringResource(R.string.ChooseYourTime),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
            )
        )

        TimeInput(
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.container_padding)
                ),
            state = timeState
        )
        Card(
            modifier = Modifier
                .padding(
                    start = dimensionResource(R.dimen.container_padding),
                    end = dimensionResource(R.dimen.container_padding)
                )
                .height(dimensionResource(R.dimen.ClickableCardHeight))
                .shadow(
                    elevation = dimensionResource(R.dimen.card_shadow_elevation),
                    clip = true,
                    spotColor = Aquamarine,
                    ambientColor = Aquamarine,
                    shape = MaterialTheme.shapes.small
                )
                .fillMaxWidth(),
            onClick = {
                onCardClick()
            },
            colors = CardDefaults.cardColors(
                containerColor = Aquamarine,
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = stringResource(R.string.FinishSetUp)
            )
        }
    }
}