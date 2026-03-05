package com.example.myhabittrackerapp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myhabittrackerapp.HabitTrackerApplication
import com.example.myhabittrackerapp.ui.screens.HabitScreenSettingsViewModel
import com.example.myhabittrackerapp.ui.screens.JournalScreenViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HabitScreenSettingsViewModel
        initializer {
            HabitScreenSettingsViewModel(
                habitTrackerApplication().container.habitRepository
            )
        }

        // Initializer for JournalScreenViewModel
        initializer {
            JournalScreenViewModel(
                habitTrackerApplication().container.journalRepository,
                habitTrackerApplication().container.habitRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [HabitTrackerApplication].
 */
fun CreationExtras.habitTrackerApplication(): HabitTrackerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HabitTrackerApplication)
