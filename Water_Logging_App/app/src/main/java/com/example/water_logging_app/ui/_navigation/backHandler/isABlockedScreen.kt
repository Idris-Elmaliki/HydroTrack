package com.example.water_logging_app.ui._navigation.backHandler

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.water_logging_app.ui._navigation.routes.AppNavRoutes
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

/*
* This is another approach to the "Global" BackHandler
* Instead of having a viewModel, instead I can just check within the set and see if the route is blocked!
*/

fun isABlockedScreen(
    destination: NavDestination?
) : Boolean {
    if(destination == null)
        return false

    val blockedRoutes = setOf(
        AppNavRoutes.SignUpScreen.name,
        SignUpPageRoutes.MainLoadingScreen.name,
        SignUpPageRoutes.BeginSignUpPage.name,
        SignUpPageRoutes.InfoScreens.name,
        SignUpPageRoutes.GetUsersNamePage.name,
        AppNavRoutes.HomePage.name,
    )

    return destination.hierarchy.any { it.route in blockedRoutes }
}