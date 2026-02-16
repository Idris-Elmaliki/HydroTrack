package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

class SignUpPageActions(
    private val navController: NavHostController
) {
    // want each page to be nested for the sign-up page!

    fun navigateToMainLoadingPage() {
        navController.navigate(SignUpPageRoutes.MainLoadingScreen)
    }

    fun navigateToBeginSignUpPage() {
        navController.navigate(SignUpPageRoutes.BeginSignUp)
    }

    fun navigateToInfoPage1() {
        navController.navigate(SignUpPageRoutes.InfoScreen1)
    }

    fun navigateToInfoPage2() {
        navController.navigate(SignUpPageRoutes.InfoScreen2)
    }

    fun navigateToInfoPage3() {
        navController.navigate(SignUpPageRoutes.InfoScreen3)
    }

    fun navigateToUsersNamePage() {
        navController.navigate(SignUpPageRoutes.UsersName)
    }

    fun navigateToUserDetailsPage() {
        navController.navigate(SignUpPageRoutes.UserData)
    }

    fun navigateToGoalsPage() {
        navController.navigate(SignUpPageRoutes.Goals)
    }

    fun navigateToLoadingPage() {
        navController.navigate(SignUpPageRoutes.LoadingScreen)
    }
}