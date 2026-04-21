package com.example.myhabittrackerapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserPreferences(
                userName = preferences[USER_NAME] ?: "",
                hasCompletedOnboarding = preferences[HAS_COMPLETED_ONBOARDING] ?: false,
                isDarkMode = preferences[IS_DARK_MODE] ?: false
            )
        }

    suspend fun saveOnboardingStatus(userName: String, hasCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
            preferences[HAS_COMPLETED_ONBOARDING] = hasCompleted
        }
    }

    suspend fun saveDarkMode(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDarkMode
        }
    }
}

data class UserPreferences(
    val userName: String,
    val hasCompletedOnboarding: Boolean,
    val isDarkMode: Boolean
)
