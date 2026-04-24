package com.example.water_logging_app.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.R
import com.example.water_logging_app.ui._navigation.navData.homepage.BottomNavList
import com.example.water_logging_app.ui._navigation.navGraphs.homeGraph
import com.example.water_logging_app.ui.homepage.viewModel.home.NotificationsViewModel
import com.example.water_logging_app.ui.subscreens.PaginationSystemUi
import com.example.water_logging_app.ui.theme.Aquamarine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUiLayout(
    modifier : Modifier,
    notifVM : NotificationsViewModel
) {
    val bottomNavController : NavHostController = rememberNavController()

    val notifData by notifVM.notifState.collectAsStateWithLifecycle()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }
    var showNotificationPages by rememberSaveable { mutableStateOf(notifData.dontShowNotificationSetUp) }

    val numList = listOf(1, 2, 3)
    val notifPageState = rememberPagerState { numList.size }

    if(!showNotificationPages) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(dimensionResource(R.dimen.container_padding)),
                    navigationIcon = {
                        Row(
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        showNotificationPages = !showNotificationPages
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
                                style = MaterialTheme.typography.labelSmall.copy(
                                    lineHeight = 0.sp
                                ),
                                color = Aquamarine
                            )
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
                        pagerList = numList,
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
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
        ) { innerpadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerpadding)
                    .padding(dimensionResource(R.dimen.container_padding))
            ) {
                when(notifPageState.currentPage) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

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
}
