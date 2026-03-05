package com.example.myhabittrackerapp.model

data class UserSettings(
    val userName: String = "Elena Thorne",
    val isDarkMode: Boolean = false,
    val dailyNudgeEnabled: Boolean = true,
    val monthlyRecapEnabled: Boolean = false,
    val reminderTime: String = "21:30"
)

data class NotificationSettings(
    val dailyNudge: Boolean = true,
    val monthlyRecap: Boolean = false,
    val reminderTime: String = "21:30"
)