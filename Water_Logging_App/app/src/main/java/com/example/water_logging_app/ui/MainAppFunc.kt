package com.example.water_logging_app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.UiNavigationRoutes


@Composable
fun MainAppFunc(
    modifier : Modifier = Modifier
) {
    val navController : NavHostController = rememberNavController()
    UiNavigationRoutes(
        navController = navController,
        modifier = modifier
    )
}