package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

class SignUpPageActions(
    private val navController: NavHostController
) {
    // want each page to be nested for the sign-up page!

    fun navigateToBeginSignUpPage() {
        navController.navigate(SignUpPageRoutes.BeginSignUpPage.name)
    }

    fun navigateToInfoPage() {
        navController.navigate(SignUpPageRoutes.InfoScreens.name)
    }
    fun navigateToUserDetailsPage() {
        navController.navigate(SignUpPageRoutes.GetUserDataPage.name)
    }

    fun navigateToUsersActivityLevelPage() {
        navController.navigate(SignUpPageRoutes.GetUsersActivityLevelPage.name)
    }

    fun navigateToUsersGoalsPage() {
        navController.navigate(SignUpPageRoutes.GetUsersGoalPage.name)
    }

    fun navigateToUsersProfilePage() {
        navController.navigate(SignUpPageRoutes.GetUsersProfilePage.name)
    }

    fun navigateToLoadingPage() {
        navController.navigate(SignUpPageRoutes.LoadingScreen.name)
    }
}