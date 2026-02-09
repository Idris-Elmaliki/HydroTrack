package com.example.water_logging_app.ui.signUpPage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.actions.AppNavActions
import com.example.water_logging_app.ui._navigation.navGraphs.SignUpGraph
import com.example.water_logging_app.ui._navigation.routes.SignUpPageRoutes

@Composable
fun SignUpPageLayout(
    mainNavActions: AppNavActions,
    modifier: Modifier
) {
    val signUpNavController = rememberNavController()

    Scaffold(
        modifier = modifier
    ) { innerpadding ->
        NavHost(
            navController = signUpNavController,
            startDestination = SignUpPageRoutes.BeginSignUp.name,
            modifier = modifier
                .padding(innerpadding)
        ) {
            SignUpGraph(
                modifier = modifier,
                navController = signUpNavController,
                mainNavActions = mainNavActions,
            )
        }
    }
}