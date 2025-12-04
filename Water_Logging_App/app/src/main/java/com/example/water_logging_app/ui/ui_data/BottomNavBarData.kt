package com.example.water_logging_app.ui.ui_data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.water_logging_app.ui.navigation.UiNavigationRoutesEnum

data class BottomNavBarData(
    val name : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val navHostName : String
)

val BottomNavList =  listOf(
    BottomNavBarData(
        name = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        navHostName = UiNavigationRoutesEnum.Setting.name
    ),
    BottomNavBarData(
        name = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        navHostName = UiNavigationRoutesEnum.Home.name
    ),
    BottomNavBarData(
        name = "History",
        selectedIcon = Icons.Filled.DateRange,
        unselectedIcon = Icons.Outlined.DateRange,
        navHostName = UiNavigationRoutesEnum.History.name
    )
)
