package com.example.water_logging_app.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui.navigation.UiNavigationRoutes
import com.example.water_logging_app.ui.ui_data.BottomNavList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterLogUiLayout(
    modifier : Modifier = Modifier,
    /*made the navController instance here, to ensure */
    navController : NavHostController = rememberNavController(),
) {
    var selectItem by remember { mutableIntStateOf(1) }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BottomNavList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if(selectItem == index) {
                                    item.selectedIcon
                                }
                                else {
                                    item.unselectedIcon
                                },
                                contentDescription = null
                            )
                        },
                        selected = (selectItem == index),
                        onClick = {
                            selectItem = index
                            navController.navigate(item.navHostName)
                        },
                        label = {
                            Text(
                                text = item.name
                            )
                        }
                    )
                }
            }
        }
    ) { innerpadding ->
        UiNavigationRoutes(
            navController = navController,
            modifier = modifier
                .padding(innerpadding)
                .safeContentPadding()
        )
    }
}