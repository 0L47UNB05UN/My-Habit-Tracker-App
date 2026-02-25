package com.example.myhabittrackerapp.ui.screens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.myhabittrackerapp.model.HabitType
import com.example.myhabittrackerapp.model.Habits
import kotlin.collections.listOf

class HabitScreenSettingsViewModel: ViewModel() {
    var habitName by mutableStateOf("Morning Meditation")
    var subtitle by mutableStateOf("meditate for few minutes in the evening")
    var habitType by mutableStateOf(HabitType.Start)
    var dailyReminderEnabled by mutableStateOf(true)
    var reminderTime by mutableStateOf("07:00 PM")
    var frequency by  mutableStateOf("Every Day")

    var currentHabit by mutableStateOf(
        Habits(
                title = "Untitled",
                subtitle = "Untitled",
                color = Color.Green,
                habitType = HabitType.Start
        )
    )

    val habits = mutableStateOf(
        listOf(
            Habits(
                title = "Drink Water",
                subtitle = "Daily goal: 8 glasses",
                habitType = HabitType.Start,
                icon = Icons.Outlined.WaterDrop,
            ),
            Habits(
                title = "Morning Meditate",
                subtitle = "10 mins • 4/7 days streak",
                habitType = HabitType.Start,
                icon = Icons.Outlined.SelfImprovement,
            ),
            Habits(
                title = "Late Night Snacking",
                subtitle = "Avoid after 9:00 PM",
                habitType = HabitType.Stop,
                icon = Icons.Outlined.Fastfood,
                color = Color.Red
                ),
            Habits(
                title = "Excessive Social Media",
                subtitle = "Limit to 30 mins daily",
                habitType = HabitType.Stop,
                icon = Icons.Outlined.Smartphone,
                color = Color.Red
            )
        )
    )

    fun save(){
        habits.value = if (currentHabit.title == "Untitled" && currentHabit.subtitle == "Untitled") {
            // New habit - add to list
            habits.value + currentHabit.copy(
                title = currentHabit.title, // Use whatever was edited
                subtitle = currentHabit.subtitle
            )
        } else {
            // Existing habit - find and replace
            habits.value.map {
                if (it.title == currentHabit.title && it.subtitle == currentHabit.subtitle)
                    currentHabit
                else
                    it
            }
        }
        Log.d("mine", "${habits.value.size} $habits.value")
    }

    private fun updateCurrentHabit() {
        currentHabit = currentHabit.copy(
            title = habitName,
            subtitle = subtitle,
            habitType = habitType
        )
    }

    fun onHabitNameChange(newName: String) {
        habitName = newName
        updateCurrentHabit()
    }

    fun onSubtitleChange(newSubtitle: String) {
        subtitle = newSubtitle
        updateCurrentHabit()
    }

    fun onHabitTypeChange(newType: HabitType) {
        habitType = newType
        updateCurrentHabit()
    }
    fun delete(){
        TODO()
    }
}