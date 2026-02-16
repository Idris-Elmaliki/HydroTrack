package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes

class AppNavActions(
    private val navController: NavHostController
) {
    fun navigateToHomePage() {
        navController.navigate(AppNavRoutes.HomePage.name)
    }

    fun navigateBackToSignUpScreen() {
        navController.popBackStack()
    }
}