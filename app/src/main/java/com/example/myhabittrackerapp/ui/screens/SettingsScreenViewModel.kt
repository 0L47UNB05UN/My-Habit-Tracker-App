package com.example.myhabittrackerapp.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabittrackerapp.data.repository.SettingsRepository
import com.example.myhabittrackerapp.model.ThemeClass
import com.example.myhabittrackerapp.model.UserSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserSettings())
    val uiState: StateFlow<UserSettings> = _uiState.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            repository.settingsFlow.collect { settings ->
                _uiState.value = settings
                _isLoading.value = false
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            repository.updateUserName(name)
        }
    }

    fun updateTheme(isDark: Boolean) {
        viewModelScope.launch {
            repository.updateTheme(isDark)
        }
    }

    fun updateThemeClass(themeClass: ThemeClass){
        viewModelScope.launch {
            repository.updateThemeClass(themeClass)
        }
    }

    fun updateDailyNudge(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateDailyNudge(enabled)
        }
    }

    fun updateMonthlyRecap(enabled: Boolean) {
        viewModelScope.launch {
            repository.updateMonthlyRecap(enabled)
        }
    }

    fun updateReminderTime(time: String) {
        viewModelScope.launch {
            repository.updateReminderTime(time)
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAllData()
        }
    }

    fun exportData() {
        viewModelScope.launch {
            val settings = repository.getSettings()
            //some other steps
        }
    }
}

