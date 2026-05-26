package com.example.water_logging_app.ui.homepage.homescreens.historySubScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.water_logging_app.R

@Composable
fun SortToggleUi(
    modifier: Modifier,
    isNewest: Boolean,
    onSortChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Sort by",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_padding))) {
            FilterChip(
                selected = isNewest,
                onClick = { onSortChange(true) },
                label = { Text("Newest") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            )
            FilterChip(
                selected = !isNewest,
                onClick = { onSortChange(false) },
                label = { Text("Oldest") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            )
        }
    }
}
