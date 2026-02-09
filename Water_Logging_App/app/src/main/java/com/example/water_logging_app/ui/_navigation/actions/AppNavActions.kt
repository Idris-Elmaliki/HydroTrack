package com.example.water_logging_app.ui._navigation.actions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes

class AppNavActions(
    private val navController: NavHostController
) {
    fun navigateBackToSignUpScreen() {
        navController.popBackStack(
            route = AppNavRoutes.SignUpScreen.name,
            inclusive = false
        )
    }
}