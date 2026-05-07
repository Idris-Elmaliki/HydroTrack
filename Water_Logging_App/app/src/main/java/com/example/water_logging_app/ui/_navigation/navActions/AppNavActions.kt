package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes

class AppNavActions(
    private val navController: NavHostController
) {
    fun navigateToHomePage() {
        navController.navigate(AppNavRoutes.HomePage.name) {
            popUpTo(0)
        }
    }

    // I think if I want an option to reset the users data in app, we can just close the app
    fun navigateBackToSignUpScreen() {
        navController.popBackStack()
    }

    fun navigateToWaterLoggingScreen() {
        navController.navigate(AppNavRoutes.WaterLogScreen.name)
    }

    fun navigateBackToHomePage() {
        navController.popBackStack(
            route = AppNavRoutes.HomePage.name,
            inclusive = false
        )
    }
}