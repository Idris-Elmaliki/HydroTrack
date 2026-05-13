package com.example.water_logging_app.ui.homepage.homescreens.profileSubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.theme.Aquamarine

@Composable
fun ProfileDrawerHeaderUi(
    modifier: Modifier,
    isEditMode: Boolean,
    onClose: () -> Unit,
    onToggleEdit: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onToggleEdit() // this will turn off editing
                onClose()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }

        Text(
            text = stringResource(R.string.Profile),
            style = MaterialTheme.typography.displaySmall,
            color = Aquamarine,
        )

        TextButton(
            onClick = onToggleEdit
        ) {
            Text(
                text =
                    if (isEditMode) { "Save" }
                    else { "Edit" },
                color = Aquamarine,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}