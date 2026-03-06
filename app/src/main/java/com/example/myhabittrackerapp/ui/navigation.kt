package com.example.myhabittrackerapp.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myhabittrackerapp.model.ThemeClass
import com.example.myhabittrackerapp.ui.screens.DiscoverScreen
import com.example.myhabittrackerapp.ui.screens.HabitScreenSettingsViewModel
import com.example.myhabittrackerapp.ui.screens.HabitSettingsScreen
import com.example.myhabittrackerapp.ui.screens.JournalScreen
import com.example.myhabittrackerapp.ui.screens.JournalScreenViewModel
import com.example.myhabittrackerapp.ui.screens.MyHabitsScreen
import com.example.myhabittrackerapp.ui.screens.OnboardingScreen
import com.example.myhabittrackerapp.ui.screens.SettingsScreen
import com.example.myhabittrackerapp.ui.screens.SettingsViewModel
import com.example.myhabittrackerapp.ui.theme.MyHabitTrackerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val habitScreenViewModel: HabitScreenSettingsViewModel = viewModel()
    val journalScreenViewModel: JournalScreenViewModel = hiltViewModel()
    val settingsScreenViewModel: SettingsViewModel = hiltViewModel()
    val uiState by settingsScreenViewModel.uiState.collectAsStateWithLifecycle()
    val isLoading by settingsScreenViewModel.isLoading.collectAsStateWithLifecycle()
    val isRegistered = uiState.userName.isNotBlank()
    var onboardingComplete by remember { mutableStateOf(false) }

    MyHabitTrackerAppTheme(
        darkTheme = when(settingsScreenViewModel.uiState.collectAsState().value.themeClass){
            ThemeClass.System -> isSystemInDarkTheme()
            ThemeClass.Light -> false
            ThemeClass.Dark -> true
        }
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            LaunchedEffect(isRegistered) {
                if (isRegistered) {
                    onboardingComplete = true
                }
            }
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                return@Surface
            }
            Scaffold(
                modifier = modifier.fillMaxSize(),
                bottomBar = {
                    if (isRegistered && onboardingComplete) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            NavigationBar(
                                modifier = modifier.fillMaxWidth(),
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                tonalElevation = 8.dp
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    NavigationBarItem(
                                        selected = currentRoute == "habit",
                                        onClick = {
                                            navController.navigate("Habit") {
                                                popUpTo(navController.graph.startDestinationId)
                                                launchSingleTop = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                Icons.Outlined.CheckCircle,
                                                contentDescription = "Habit"
                                            )
                                        },
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
                                        icon = {
                                            Icon(
                                                Icons.Outlined.Book,
                                                contentDescription = "Journal"
                                            )
                                        },
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
                                        icon = {
                                            Icon(
                                                Icons.Outlined.Explore,
                                                contentDescription = "Discover"
                                            )
                                        },
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
                                        icon = {
                                            Icon(
                                                Icons.Outlined.Settings,
                                                contentDescription = "Settings"
                                            )
                                        },
                                        label = { Text("Settings") }
                                    )
                                }
                            }
                        }
                    }
                }
            ) { paddingValues ->
                if (isRegistered && onboardingComplete) {
                    NavHost(
                        navController = navController,
                        startDestination = "discover",
                    ) {
                        composable("discover") {
                            DiscoverScreen(
                                onHabitClick = {
                                    habitScreenViewModel.markCurrentHabit(it)
                                    navController.navigate("habitSettings")
                                }
                            )
                        }
                        composable("journal") {
                            JournalScreen(journalScreenViewModel)
                        }
                        composable("settings") {
                            SettingsScreen(settingsScreenViewModel)
                        }
                        composable("habit") {
                            MyHabitsScreen(
                                habitScreenViewModel,
                                onNavigate = {
                                    navController.navigate("habitSettings")
                                }
                            )
                        }
                        composable("habitSettings") {
                            HabitSettingsScreen(
                                habitScreenViewModel,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                } else {
                    OnboardingScreen(
                        paddingValues = paddingValues,
                        viewModel = settingsScreenViewModel,
                        onOnboardingComplete = {
                            onboardingComplete = true
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}