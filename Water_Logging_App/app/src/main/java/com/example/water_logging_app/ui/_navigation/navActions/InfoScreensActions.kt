package com.example.water_logging_app.ui._navigation.navActions

import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.InfoPageRoutes

class InfoScreensActions(
    private val navController: NavHostController
) {
    fun navigateToInfoScreen1() {
        navController.navigate(InfoPageRoutes.InfoScreen1.name)
    }

    fun navigateToInfoScreen2() {
        navController.navigate(InfoPageRoutes.InfoScreen2.name)
    }

    fun navigateToInfoScreen3() {
        navController.navigate(InfoPageRoutes.InfoScreen3.name)
    }
}