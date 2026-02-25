package com.example.myhabittrackerapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DepartureBoard
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color



data class Habits(
    val title: String,
    val subtitle: String,
    val habitType: HabitType = HabitType.Start,
    val color: Color = Color.Green,
    val icon: ImageVector = Icons.Outlined.DepartureBoard
)

enum class HabitType(val displayName: String) {
    Start("Start Habit"),
    Stop("Stop Habit")
}