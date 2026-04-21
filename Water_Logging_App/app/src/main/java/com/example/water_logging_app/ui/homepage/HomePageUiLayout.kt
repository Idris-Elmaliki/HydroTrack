package com.example.water_logging_app.ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.R
import com.example.water_logging_app.ui._navigation.navData.homepage.BottomNavList
import com.example.water_logging_app.ui._navigation.navGraphs.homeGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUiLayout(
    modifier : Modifier = Modifier
) {
    // here I made the nav Controller for ONLY the bottom navigation bar
    // This allows me to have full control of the Nav Graph, while having nested nav graphs!
    val bottomNavController : NavHostController = rememberNavController()

    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

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
                                imageVector = if (selectedItem == index) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = null
                            )
                        },
                        selected = (selectedItem == index),
                        onClick = {
                            selectedItem = index
                            bottomNavController.navigate(item.navHostName)
                        },
                        alwaysShowLabel = false
                    )
                }
            }

        }
    ) { innerpadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home_graph",
            modifier = Modifier
                .padding(innerpadding)
        ) {
            homeGraph(
                navController = bottomNavController,
                modifier = modifier
            )
        }
    }
}