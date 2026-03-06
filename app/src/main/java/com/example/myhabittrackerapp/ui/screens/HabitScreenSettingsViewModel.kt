package com.example.myhabittrackerapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabittrackerapp.data.repository.HabitRepository
import com.example.myhabittrackerapp.model.Habits
import com.example.myhabittrackerapp.model.HabitType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitScreenSettingsViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    var title by mutableStateOf("")
    var subtitle by mutableStateOf("")
    var habitType by mutableStateOf(HabitType.Start)
    var dailyReminderEnabled by mutableStateOf(true)
    var reminderTime by mutableStateOf("07:00 PM")
    var frequency by mutableStateOf("Every Day")

    var originalTitle by mutableStateOf("")
    var originalSubtitle by mutableStateOf("")
    var originalId by mutableStateOf<String?>(null)

    var currentHabit by mutableStateOf(
        Habits(
            title = "Untitled",
            subtitle = "Untitled",
            color = Color.Green,
            habitType = HabitType.Start
        )
    )

    private val _habits = MutableStateFlow<List<Habits>>(emptyList())
    val habits: StateFlow<List<Habits>> = _habits.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadHabits()
    }

    private fun loadHabits() {
        viewModelScope.launch {
            repository.allHabits.collect { habitList ->
                _habits.value = habitList
                _isLoading.value = false
            }
        }
    }



    fun resetCurrentHabit() {
        markCurrentHabit(
            Habits(
                title = "Untitled",
                subtitle = "Untitled",
                color = Color.Green,
                habitType = HabitType.Start
            )
        )
    }

    fun markCurrentHabit(habit: Habits) {
        currentHabit = habit
        title = habit.title
        subtitle = habit.subtitle
        habitType = habit.habitType
        originalTitle = habit.title
        originalSubtitle = habit.subtitle
        originalId = habit.id
    }

    fun save() {
        viewModelScope.launch {
            if (originalId == null) {
                // New habit
                val newHabit = Habits(
                    title = title,
                    subtitle = subtitle,
                    habitType = habitType,
                    color = if (habitType == HabitType.Start) Color.Green else Color.Red,
                    icon = currentHabit.icon
                )
                repository.insertHabit(newHabit)
            } else {
                val updatedHabit = Habits(
                    id = originalId,
                    title = title,
                    subtitle = subtitle,
                    habitType = habitType,
                    color = if (habitType == HabitType.Start) Color.Green else Color.Red,
                    icon = currentHabit.icon
                )
                repository.updateHabit(updatedHabit)
            }

            title = ""
            subtitle = ""
            habitType = HabitType.Start
            originalTitle = ""
            originalSubtitle = ""
            originalId = null
        }
    }

    fun delete() {
        viewModelScope.launch {
            if (originalId != null) {
                val habitToDelete = Habits(
                    id = originalId,
                    title = title,
                    subtitle = subtitle,
                    habitType = habitType,
                    color = if (habitType == HabitType.Start) Color.Green else Color.Red,
                    icon = currentHabit.icon
                )
                repository.deleteHabit(habitToDelete)
                resetCurrentHabit()
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
}