package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.viewModel.SignUpViewModel

@Composable
fun UserDailyGoalScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
) {
    val state by signUpVM.signUpData.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoading) {
        signUpVM.uploadUserData()
    }

    LoadingScreen(
        modifier = modifier,
    ) {}


}