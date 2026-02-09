package com.example.water_logging_app.ui._navigation.actions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

class SignUpPageActions(
    private val navController: NavHostController
) {
    // want each page to be nested for the sign up page!

    fun navigateToUserDetailsPage() {
        navController.navigate(SignUpPageRoutes.UserData)
    }

    fun navigateToMeasurementTypePage() {
        navController.navigate(SignUpPageRoutes.MeasurementType)
    }

    fun navigateToGoalPage() {
        navController.navigate(SignUpPageRoutes.Goal)
    }

    fun navigateToLoadingPage() {
        navController.navigate(SignUpPageRoutes.LoadingScreen)
    }

    fun navigateToHomePage() {
        navController.navigate(AppNavRoutes.HomePage)
    }
}