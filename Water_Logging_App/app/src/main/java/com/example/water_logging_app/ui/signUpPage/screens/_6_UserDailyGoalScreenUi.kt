package com.example.water_logging_app.ui.signUpPage.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.signUpPage.screens.subscreens.LoadingScreen
import com.example.water_logging_app.ui.signUpPage.viewModels.parent.SignUpViewModel

@Composable
fun UserDailyGoalScreen(
    modifier : Modifier,
    signUpVM : SignUpViewModel,
    currentNavAction : () -> Unit,
) {
    LoadingScreen(
        modifier = modifier,
    ) {
        Spacer(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.extra_container_padding))
        )

        LoadingBar()

        Spacer(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.container_padding))
        )

        Text(
            text = stringResource(R.string.CalculatingRecommendedGoal),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun LoadingBar() {

}