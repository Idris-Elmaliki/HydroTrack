package com.example.water_logging_app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.water_logging_app.R
import com.example.water_logging_app.ui.ui_data.BottomNavList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterLogUiLayout(
    modifier : Modifier = Modifier
) {
    var selectItem by remember { mutableStateOf(0) }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BottomNavList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if(selectItem == index) {
                                    item.selectedIcon
                                }
                                else {
                                    item.unselectedIcon
                                },
                                contentDescription = null
                            )
                        },
                        selected = (selectItem == index),
                        onClick = {
                            selectItem = index
                        },
                        label = {
                            Text(
                                text = item.name
                            )
                        }
                    )
                }
            }
        }
    ) {

    }
}