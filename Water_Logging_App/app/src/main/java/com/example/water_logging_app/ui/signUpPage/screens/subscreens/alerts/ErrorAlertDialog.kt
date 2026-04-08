package com.example.water_logging_app.ui.signUpPage.screens.subscreens.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.poppins

/*
* This subscreen is used in both the UserNamePage and UserDataPage,
*
* The main usage of this dialog is to tell the user that there is missing information!!!!
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowErrorDialogUi(
    modifier : Modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = dimensionResource(R.dimen.extra_container_padding),
            bottom = dimensionResource(R.dimen.extra_container_padding),
            start = dimensionResource(R.dimen.container_padding),
            end = dimensionResource(R.dimen.container_padding)
        ),
    errorList : List<String>,
    onDismiss : () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
        },
    ) {
        Card(
            modifier = modifier
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding))
            ) {
                Text(
                    text = stringResource(R.string.MissingData),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins
                    ),
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding))
                )
                Text(
                    text = stringResource(R.string.InOrderToContinue),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding))
                )
                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.text_padding)))
                errorList.forEach { error ->
                    Text(
                        text = error, // probably doesn't work?
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}