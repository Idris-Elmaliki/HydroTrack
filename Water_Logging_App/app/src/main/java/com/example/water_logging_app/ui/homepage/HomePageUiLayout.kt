package com.example.water_logging_app.ui.homepage

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.water_logging_app.ui._navigation.navData.homepage.BottomNavList
import com.example.water_logging_app.ui._navigation.UiNavigationRoutesEnum
import com.example.water_logging_app.ui._navigation.homeGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageUiLayout(
    modifier : Modifier = Modifier,
    rootNavController : NavController,
) {
    // here I made the nav Controller for ONLY the bottom navigation bar
    // This allows me to have full control of the Nav Graph, while having nested nav graphs!
    val bottomNavController : NavHostController = rememberNavController()

    var selectItem by remember { mutableIntStateOf(1) }

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
                                imageVector = if (selectItem == index) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = null
                            )
                        },
                        selected = (selectItem == index),
                        onClick = {
                            selectItem = index
                            bottomNavController.navigate(item.navHostName)
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
    ) { innerpadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = UiNavigationRoutesEnum.Home.name,
            modifier = Modifier
                .padding(innerpadding)
        ) {
            homeGraph(
                navController = rootNavController,
                modifier = modifier
            )
        }
    }
}
