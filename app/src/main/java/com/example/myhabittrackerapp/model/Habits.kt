package com.example.myhabittrackerapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DepartureBoard
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


data class Habits(
    val id: String? = null, // Add ID for database
    val title: String,
    val subtitle: String,
    val habitType: HabitType = HabitType.Start,
    val color: Color = if (habitType == HabitType.Start) Color.Green else Color.Red,
    val icon: ImageVector = Icons.Outlined.DepartureBoard
)

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val subtitle: String,
    val habitType: HabitType,
    val iconName: String, // Store icon name as string
    val colorHex: Long,   // Store color as Long
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
enum class HabitType(val displayName: String) {
    Start("Start Habit"),
    Stop("Stop Habit")
}