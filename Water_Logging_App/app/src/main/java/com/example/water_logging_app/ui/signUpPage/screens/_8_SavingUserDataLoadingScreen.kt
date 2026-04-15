package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.ProfilePictureViewModel
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel

@Composable
fun SaveUserDataLoadingScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    profilePicVM : ProfilePictureViewModel,
    toHomePage : () -> Unit, // go to notifications screen! (will be in the homepage!)
) {
    val signUpData by signUpVM.signUpData.collectAsStateWithLifecycle()
    val profilePickData by profilePicVM.profilePictureUri.collectAsStateWithLifecycle()

    // I need to wrap this process with WorkManager

    LaunchedEffect(Unit) {
        signUpVM.updateUserData()
        profilePicVM.saveUserProfilePicture()
    }

    LaunchedEffect(signUpData, profilePickData) {
        if (!signUpData.isLoading && !profilePickData.isLoading)
            toHomePage()
    }

    if(signUpData.isLoading && profilePickData.isLoading) {
        LoadingScreen(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding)))
        }
    }
}