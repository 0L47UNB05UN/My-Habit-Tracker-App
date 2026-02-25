package com.example.myhabittrackerapp.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myhabittrackerapp.ui.screens.DiscoverScreen
import com.example.myhabittrackerapp.ui.screens.HabitScreenSettingsViewModel
import com.example.myhabittrackerapp.ui.screens.HabitSettingsScreen
import com.example.myhabittrackerapp.ui.screens.JournalScreen
import com.example.myhabittrackerapp.ui.screens.MyHabitsScreen
import com.example.myhabittrackerapp.ui.screens.OnboardingScreen
import com.example.myhabittrackerapp.ui.screens.SettingsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val habitScreenViewModel: HabitScreenSettingsViewModel = viewModel()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier.fillMaxWidth()
            ){
                NavigationBar(
                    modifier = modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    tonalElevation = 8.dp
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
                    ) {
                        NavigationBarItem(
                            selected = currentRoute == "habit",
                            onClick = {
                                navController.navigate("Habit") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Outlined.CheckCircle, contentDescription = "Habit") },
                            label = { Text("Habit") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == "journal",
                            onClick = {
                                navController.navigate("journal") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Outlined.Book, contentDescription = "Journal") },
                            label = { Text("Journal") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == "discover",
                            onClick = {
                                navController.navigate("discover") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Outlined.Explore, contentDescription = "Discover") },
                            label = { Text("Discover") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == "settings",
                            onClick = {
                                navController.navigate("settings") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                            label = { Text("Settings") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == "home",
                            onClick = {
                                navController.navigate("home") {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
                            label = { Text("Home") }
                        )
                    }
                }
            }
        },

    ) {
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                OnboardingScreen(paddingValues)
            }
            composable("discover") {
                DiscoverScreen()
            }
            composable("journal"){
                JournalScreen()
            }
            composable("settings"){
                SettingsScreen()
            }
            composable("habit"){
                MyHabitsScreen(habitScreenViewModel, onNavigate = { navController.navigate("habitSetting")})
            }
            composable("habitSetting"){
                HabitSettingsScreen(
                    habitScreenViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}