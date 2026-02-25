package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

class SignUpPageActions(
    private val navController: NavHostController
) {
    // want each page to be nested for the sign-up page!

    fun navigateToMainLoadingPage() {
        navController.navigate(SignUpPageRoutes.MainLoadingScreen.name)
    }

    fun navigateToBeginSignUpPage() {
        navController.navigate(SignUpPageRoutes.BeginSignUpPage.name)
    }

    fun navigateToInfoPage1() {
        navController.navigate(SignUpPageRoutes.InfoScreens.name)
    }

    fun navigateToUsersProfilePage() {
        navController.navigate(SignUpPageRoutes.GetUsersProfilePage.name)
    }

    fun navigateToUserDetailsPage() {
        navController.navigate(SignUpPageRoutes.GetUserDataPage.name)
    }

    fun navigateToUsersGoalsPage() {
        navController.navigate(SignUpPageRoutes.GetUsersGoalPage.name)
    }

    fun navigateToLoadingPage() {
        navController.navigate(SignUpPageRoutes.LoadingScreen.name)
    }
}