package com.example.water_logging_app.ui.homepage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.R
import com.example.water_logging_app.ui._navigation.navData.homepage.BottomNavList
import com.example.water_logging_app.ui._navigation.navGraphs.homeGraph
import com.example.water_logging_app.ui.homepage.viewModel.home.NotificationsViewModel
import com.example.water_logging_app.ui.subscreens.PaginationSystemUi
import com.example.water_logging_app.ui.subscreens.alerts.ConfirmationAlertDialog
import com.example.water_logging_app.ui.theme.Aquamarine
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

    // the variable captures the default value of dontShowNotificationSetUp (which is set to false)
    var showNotificationSetupPages by rememberSaveable { mutableStateOf(!notifData.dontShowNotificationSetUp) }

    // this captures the actual DataStore value, since by them the function call in init {} will be complete!
    LaunchedEffect(notifData.isLoading) {
        if(!notifData.isLoading) {
            showNotificationSetupPages = !notifData.dontShowNotificationSetUp
        }
    }

    // it's best to make everything into a when statement
    when(notifData.isLoading) {
        true -> {
            CircularProgressIndicator()
        }
        false -> {
            val pageCount = rememberSaveable {
                if (notifData.allowNotifications && notifData.notificationTime == null) 2 else 4
            }

            val notifPageState = rememberPagerState { pageCount }

            if(!notifData.dontShowNotificationSetUp) {
                NotifPaginationUi(
                    modifier = modifier,
                    notifVM = notifVM,
                    notifPageState = notifPageState,
                    onShowNotifChange = { showNotificationSetupPages = !showNotificationSetupPages }
                )
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotifPaginationUi(
    modifier: Modifier,
    notifVM: NotificationsViewModel,
    notifPageState : PagerState,
    onShowNotifChange : () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var checkBoxInput by rememberSaveable { mutableStateOf(false)}
    var confirmUserCompletion by rememberSaveable { mutableStateOf(false) } // used either when the user presses cancel/finish

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
                                        confirmUserCompletion = !confirmUserCompletion
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
                    Checkbox(
                        checked = checkBoxInput,
                        onCheckedChange = {
                            checkBoxInput = !checkBoxInput
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
        if(notifPageState.pageCount == 2) {
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
                        NotificationSetUpTransitionUi {
                            coroutineScope.launch {
                                notifPageState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                    1 -> {
                        NotificationTimeSetUpUi(
                            modifier = Modifier.fillMaxSize(),
                            notifVM = notifVM,
                            onCardClick = {
                                confirmUserCompletion = !confirmUserCompletion
                            }
                        )
                    }
                }
            }
        }
        else {
            HorizontalPager(
                state = notifPageState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerpadding)
                    .padding(dimensionResource(R.dimen.container_padding)),
                userScrollEnabled = false // Keep control via buttons/logic
            ) { page ->
                when(page) {
                    0 -> {
                        NotificationSetUpTransitionUi {
                            coroutineScope.launch {
                                notifPageState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                    1 -> {
                        TellUserAboutNotifPopUpUi {
                            coroutineScope.launch {
                                notifPageState.animateScrollToPage(page + 1)
                            }
                        }
                    }
                    2 -> {
                        NotifLauncherUi(
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
                            onCardClick = {
                                confirmUserCompletion = !confirmUserCompletion
                            }
                        )
                    }
                }
            }
        }
    }

    if(confirmUserCompletion) {
        ConfirmationAlertDialog(
            onDismiss = {
                confirmUserCompletion = !confirmUserCompletion
            },
            onContinuation = {
                notifVM.updateShowNotificationSetUp(checkBoxInput)
                onShowNotifChange()
            }
        )
    }
}

@Composable
private fun NotificationSetUpTransitionUi(
    onCardClick : () -> Unit
) {
    Column(
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
    onLauncherResult: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(!isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val isPermanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.POST_NOTIFICATIONS
                )

                if (isPermanentlyDenied) {
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    context.startActivity(intent)
                }
            }
            else {
                run {}
            }
        }
        else {
            notifVM.updateAllowNotifications(
                allowNotifications = true
            )
            onLauncherResult()
        }
    }

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
                    end = dimensionResource(R.dimen.container_padding),
                    bottom = dimensionResource(R.dimen.text_padding)
                )
                .height(dimensionResource(R.dimen.ClickableCardHeight))
                .shadow(
                    elevation = dimensionResource(R.dimen.card_shadow_elevation),
                    clip = true,
                    spotColor = Aquamarine,
                    ambientColor = Aquamarine,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(),
            onClick = {
                if(Build.VERSION.SDK_INT >= 33) {
                    coroutineScope.launch {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
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
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(),
            onClick = {
                onLauncherResult()
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
                    text = stringResource(R.string.Decline),
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
    onCardClick: () -> Unit
) {
    val timeState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = DateFormat.is24HourFormat(LocalContext.current),
    )

    LaunchedEffect(timeState) {
        notifVM.updateNotificationTime(
            LocalTime.of(timeState.hour, timeState.minute)
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.container_padding)
                ),
            text = stringResource(R.string.ChooseYourTime),
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
            )
        )

        Text(
            modifier = Modifier
                .alpha(0.7f)
                .padding(
                    bottom = dimensionResource(R.dimen.text_padding)
                ),
            text = stringResource(R.string.AboutNotifications),
            style = MaterialTheme.typography.labelSmall.copy(
                textAlign = TextAlign.Center
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
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.FinishSetUp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 0.sp
                    )
                )
            }
        }
    }
}