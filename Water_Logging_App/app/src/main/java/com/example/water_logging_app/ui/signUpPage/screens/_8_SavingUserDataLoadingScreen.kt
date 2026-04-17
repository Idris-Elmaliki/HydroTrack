package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.animations.DotLoadingAnimation
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.ProfilePictureViewModel
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel
import kotlinx.coroutines.delay

@Composable
fun SaveUserDataLoadingScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    profilePicVM : ProfilePictureViewModel,
    toHomePage : () -> Unit // go to notifications screen! (will be in the homepage!)
) {
    val signUpData by signUpVM.signUpData.collectAsStateWithLifecycle()
    val profilePickData by profilePicVM.profilePictureUri.collectAsStateWithLifecycle()

    var toHomePageAction by rememberSaveable { mutableStateOf(false) }

    // I need to wrap this process with WorkManager

    LaunchedEffect(Unit) {
        delay(3000L)

        signUpVM.uploadUserData()
        profilePicVM.uploadUserProfilePicture()

        delay(3000L)

        toHomePageAction = true
    }

    if(signUpData.isLoading && profilePickData.isLoading) {
        LoadingScreen(
            modifier = modifier
        ) {
            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding)))

            DotLoadingAnimation()

            Spacer(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding)))

            LoadingAnimationText()
        }
    }
    else {
        if(toHomePageAction) {
            toHomePage()
        }
    }
}

@Composable
private fun LoadingAnimationText() {
    Text(
        text = "Saving Your Data",
        style = MaterialTheme.typography.bodyLarge
    )
}