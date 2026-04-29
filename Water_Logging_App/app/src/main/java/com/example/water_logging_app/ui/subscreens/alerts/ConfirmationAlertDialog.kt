package com.example.water_logging_app.ui.subscreens.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.poppins

/*
* This subscreen is used in both the ActivityLevel and UserProfile screens
*
* The main usage of this dialog is to confirm to the user if there is he/she would like to move forward
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationAlertDialog(
    modifier : Modifier = Modifier
        .fillMaxWidth()
        .padding(
            top = dimensionResource(R.dimen.extra_container_padding),
            bottom = dimensionResource(R.dimen.extra_container_padding),
            start = dimensionResource(R.dimen.container_padding),
            end = dimensionResource(R.dimen.container_padding)
        ),
    onDismiss : () -> Unit,
    onContinuation: () -> Unit,
    showDismiss : Boolean = true
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.container_padding)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding)),
                    text = stringResource(R.string.Confirm),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppins
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(top = dimensionResource(R.dimen.text_padding), bottom = dimensionResource(R.dimen.text_padding)),
                    text = stringResource(R.string.ConfirmMessage),
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    modifier = Modifier
                        .alpha(0.75f),
                    text = stringResource(R.string.ChangeDataLaterMessage),
                    style = MaterialTheme.typography.labelSmall,
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = dimensionResource(R.dimen.container_padding),
                            bottom = dimensionResource(R.dimen.text_padding)
                        )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if(showDismiss) {
                        TextButton(
                            shape = CircleShape,
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.Cancel).trim(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier
                                    .padding(dimensionResource(R.dimen.text_padding))
                            )
                        }
                        VerticalDivider(
                            modifier = Modifier
                                .height(dimensionResource(R.dimen.divider_height))
                        )
                    }
                    TextButton(
                        shape = CircleShape,
                        onClick = {
                            onDismiss()
                            onContinuation()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.Confirm).trim(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.text_padding)),
                        )
                    }
                }
            }
        }
    }
}