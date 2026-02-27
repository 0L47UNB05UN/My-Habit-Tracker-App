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
    var title by mutableStateOf("")
    var subtitle by mutableStateOf("")
    var habitType by mutableStateOf(HabitType.Start)
    var dailyReminderEnabled by mutableStateOf(true)
    var reminderTime by mutableStateOf("07:00 PM")
    var frequency by  mutableStateOf("Every Day")

    var originalTitle by mutableStateOf("")
    var originalSubtitle by mutableStateOf("")

    var currentHabit by mutableStateOf(
        Habits(
                title = "Untitled",
                subtitle = "Untitled",
                color = Color.Green,
                habitType = HabitType.Start
        )
    )
    val habits = mutableStateOf(listOf(
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
        ))

    fun resetCurrentHabit(){
        markCurrentHabit(
            Habits(
                title = "Untitled",
                subtitle = "Untitled",
                color = Color.Green,
                habitType = HabitType.Start
            )
        )
    }
    fun markCurrentHabit(habit: Habits){
        currentHabit = habit
        title = habit.title
        subtitle = habit.subtitle
        habitType = habit.habitType
        originalTitle = title
        originalSubtitle = subtitle
    }
    fun save(){
        habits.value = if (originalTitle == "Untitled" && originalSubtitle == "Untitled") {
            val newHabit = Habits(
                title = title,
                subtitle = subtitle,
                habitType = habitType,
                color = if (habitType == HabitType.Start) Color.Green else Color.Red,
                icon = currentHabit.icon
            )
            if (!habits.value.contains(newHabit)) habits.value + newHabit else habits.value
        } else {
            habits.value.map {
                if (it.title == originalTitle && it.subtitle == originalSubtitle)
                    it.copy(
                        title = title,
                        subtitle = subtitle,
                        habitType = habitType,
                        color = if (habitType == HabitType.Start) Color.Green else Color.Red
                    )
                else
                    it
            }
        }
    }

    private fun updateCurrentHabit() {
        currentHabit = currentHabit.copy(
            title = title,
            subtitle = subtitle,
            habitType = habitType,
            color = if (habitType == HabitType.Start) Color.Green else Color.Red
        )
    }

    fun onHabitNameChange(newName: String) {
        title = newName
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
    fun delete() {
            habits.value = habits.value.filter { habit ->
                !(habit.title == originalTitle && habit.subtitle == originalSubtitle)
            }
    }
}