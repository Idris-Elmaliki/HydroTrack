package com.example.water_logging_app.ui.signUpPage.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

// doesn't work :(
//
// (for now)!
class BackHandlerViewModel() : ViewModel() {
    private var _backPressed : MutableSharedFlow<String> = MutableSharedFlow()
    val backPressed : SharedFlow<String> = _backPressed.asSharedFlow()

    fun onBackPressed(navController: NavHostController) {
        val currentRoute = navController.currentBackStackEntry?.destination?.route

        when (currentRoute) {
            SignUpPageRoutes.MainLoadingScreen.name,
            SignUpPageRoutes.BeginSignUpPage.name,
            SignUpPageRoutes.InfoScreens.name -> {}
            else -> {
                navController.popBackStack()
            }
        }
    }
}