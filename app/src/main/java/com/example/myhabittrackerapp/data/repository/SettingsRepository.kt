package com.example.myhabittrackerapp.data.repository

import android.content.res.Resources
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myhabittrackerapp.model.ThemeClass
import com.example.myhabittrackerapp.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val USER_NAME = stringPreferencesKey("")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val THEME_CLASS = stringPreferencesKey(ThemeClass.System.name)
        val DAILY_NUDGE_ENABLED = booleanPreferencesKey("daily_nudge_enabled")
        val MONTHLY_RECAP_ENABLED = booleanPreferencesKey("monthly_recap_enabled")
        val REMINDER_TIME = stringPreferencesKey("reminder_time")
    }

    val settingsFlow: Flow<UserSettings> = dataStore.data
        .map { preferences ->
            UserSettings(
                userName = preferences[USER_NAME] ?: "",
                isDarkMode = preferences[IS_DARK_MODE] ?: false,
                themeClass = ThemeClass.valueOf(preferences[THEME_CLASS]?: ThemeClass.System.name),
                dailyNudgeEnabled = preferences[DAILY_NUDGE_ENABLED] ?: true,
                monthlyRecapEnabled = preferences[MONTHLY_RECAP_ENABLED] ?: false,
                reminderTime = preferences[REMINDER_TIME] ?: "21:30"
            )
        }

    suspend fun getSettings(): UserSettings {
        val preferences = dataStore.data.first()
        return UserSettings(
            userName = preferences[USER_NAME] ?: "Elena Thorne",
            isDarkMode = preferences[IS_DARK_MODE] ?: false,
            themeClass = ThemeClass.valueOf(preferences[THEME_CLASS]?: ThemeClass.System.name),
            dailyNudgeEnabled = preferences[DAILY_NUDGE_ENABLED] ?: true,
            monthlyRecapEnabled = preferences[MONTHLY_RECAP_ENABLED] ?: false,
            reminderTime = preferences[REMINDER_TIME] ?: "21:30"
        )
    }

    // Write operations
    suspend fun updateUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    suspend fun updateTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    suspend fun updateThemeClass(themeClass: ThemeClass){
        dataStore.edit{
            preferences -> preferences[THEME_CLASS] = themeClass.name
        }
    }

    suspend fun updateDailyNudge(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DAILY_NUDGE_ENABLED] = enabled
        }
    }

    suspend fun updateMonthlyRecap(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[MONTHLY_RECAP_ENABLED] = enabled
        }
    }

    suspend fun updateReminderTime(time: String) {
        dataStore.edit { preferences ->
            preferences[REMINDER_TIME] = time
        }
    }

    suspend fun clearAllData() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}