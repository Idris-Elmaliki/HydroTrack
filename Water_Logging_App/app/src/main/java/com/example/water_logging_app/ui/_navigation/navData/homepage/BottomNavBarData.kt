package com.example.water_logging_app.ui._navigation.navData.homepage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.water_logging_app.ui._navigation.routes.HomePageRoutes

sealed class BottomNavBarData(
    val name : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val navHostName : String
)

object Home : BottomNavBarData(
    name = "Home",
    selectedIcon = Icons.Filled.Home,
    unselectedIcon = Icons.Outlined.Home,
    navHostName = HomePageRoutes.Home.name
)

object Settings : BottomNavBarData(
    name = "Settings",
    selectedIcon = Icons.Filled.Settings,
    unselectedIcon = Icons.Outlined.Settings,
    navHostName = HomePageRoutes.Setting.name
)

object History : BottomNavBarData(
    name = "History",
    selectedIcon = Icons.Filled.DateRange,
    unselectedIcon = Icons.Outlined.DateRange,
    navHostName = HomePageRoutes.History.name
)

val BottomNavList =  listOf(
    Settings, Home, History
)
