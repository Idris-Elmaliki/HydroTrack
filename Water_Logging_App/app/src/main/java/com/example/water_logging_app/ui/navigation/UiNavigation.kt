package com.example.water_logging_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.water_logging_app.ui.screens.HistoryScreen
import com.example.water_logging_app.ui.screens.HomeScreen
import com.example.water_logging_app.ui.screens.SettingScreen

enum class UiNavigationRoutesEnum() {
    Home,
    Setting,
    History
}

@Composable
fun UiNavigationRoutes(
    navController : NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = UiNavigationRoutesEnum.Home.name,
        modifier = modifier
    ) {
        composable(
            route = UiNavigationRoutesEnum.Home.name
        ) {
            HomeScreen(
                modifier = modifier
            )
        }
        composable(
            route = UiNavigationRoutesEnum.Setting.name
        ) {
            SettingScreen(
                modifier = modifier
            )
        }
        composable(
            route = UiNavigationRoutesEnum.History.name
        ) {
            HistoryScreen(
                modifier = modifier
            )
        }
    }
}

