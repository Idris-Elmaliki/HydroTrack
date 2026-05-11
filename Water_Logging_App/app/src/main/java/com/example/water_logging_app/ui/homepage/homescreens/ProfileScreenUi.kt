package com.example.water_logging_app.ui.homepage.homescreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.water_logging_app.R
import com.example.water_logging_app.photoPicker.domain.modelData.PhotoData
import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUi(
    userState : UserPreferenceData,
    photoState : PhotoData,
    onDismiss : () -> Unit,
    onSave : () -> Unit
) {
    var isInEditMode by rememberSaveable { mutableStateOf(false) }

    ModalDrawerSheet {
        ProfileDrawerHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.text_padding),
                    vertical = dimensionResource(R.dimen.mini_text_padding)
                ),
            isEditMode = isInEditMode,
            onClose = onDismiss,
            onToggleEdit = onSave
        )

        HorizontalDivider()
    }
}

@Composable
fun ProfileDrawerHeader(
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
            onClick = onClose
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }

        Text(
            text = stringResource(R.string.Profile),
            style = MaterialTheme.typography.titleMedium
        )

        TextButton(
            onClick = onToggleEdit
        ) {
            Text(text = if (isEditMode) "Save" else "Edit")
        }
    }
}