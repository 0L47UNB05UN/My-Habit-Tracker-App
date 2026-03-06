package com.example.myhabittrackerapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabittrackerapp.model.HabitRepository
import com.example.myhabittrackerapp.model.HabitType
import com.example.myhabittrackerapp.model.Habits
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitScreenSettingsViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    var title by mutableStateOf("")
    var subtitle by mutableStateOf("")
    var habitType by mutableStateOf(HabitType.Start)
    var dailyReminderEnabled by mutableStateOf(true)
    var reminderTime by mutableStateOf("07:00 PM")
    var frequency by mutableStateOf("Every Day")

    var currentHabit by mutableStateOf<Habits?>(null)

    val habits: StateFlow<List<Habits>> = habitRepository.allHabits
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun resetCurrentHabit() {
        currentHabit = null
        title = ""
        subtitle = ""
        habitType = HabitType.Start
    }

    fun markCurrentHabit(habit: Habits) {
        currentHabit = habit
        title = habit.title
        subtitle = habit.subtitle
        habitType = habit.habitType
    }

    fun save() {
        viewModelScope.launch {
            val habit = currentHabit?.copy(
                title = title,
                subtitle = subtitle,
                habitType = habitType,
                colorArgb = if (habitType == HabitType.Start) Color.Green.toArgb() else Color.Red.toArgb()
            ) ?: Habits(
                title = title,
                subtitle = subtitle,
                habitType = habitType,
                colorArgb = if (habitType == HabitType.Start) Color.Green.toArgb() else Color.Red.toArgb(),
                iconName = "CheckCircle"
            )

            if (habit.id == 0L) {
                habitRepository.insertHabit(habit)
            } else {
                habitRepository.updateHabit(habit)
            }
        }
    }

    fun delete() {
        currentHabit?.let {
            viewModelScope.launch {
                habitRepository.deleteHabit(it)
            }
        }
    }

    fun onHabitNameChange(newName: String) {
        title = newName
    }

    fun onSubtitleChange(newSubtitle: String) {
        subtitle = newSubtitle
    }

    fun onHabitTypeChange(newType: HabitType) {
        habitType = newType
    }
}
