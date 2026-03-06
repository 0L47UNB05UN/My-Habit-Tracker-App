package com.example.myhabittrackerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habits(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val subtitle: String,
    val habitType: HabitType = HabitType.Start,
    val colorArgb: Int,
    val iconName: String,
    val reminderTime: String? = null,
    val isReminderEnabled: Boolean = true
)

enum class HabitType(val displayName: String) {
    Start("Start Habit"),
    Stop("Stop Habit")
}
