package com.example.myhabittrackerapp.model

import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class ExportData(
    val habits: List<HabitExport>,
    val journals: List<JournalExport>
)

@Serializable
data class HabitExport(
    val title: String,
    val subtitle: String,
    val habitType: String,
    val colorArgb: Int,
    val iconName: String,
    val reminderTime: String?,
    val isReminderEnabled: Boolean
)

@Serializable
data class JournalExport(
    val date: String,
    val content: String,
    val mood: String,
    val isCompleted: Boolean
)

@Singleton
class DataExportRepository @Inject constructor(
    private val habitRepository: HabitRepository,
    private val journalRepository: JournalRepository
) {
    suspend fun exportDataToJson(): String {
        val habits = habitRepository.allHabits.first().map {
            HabitExport(it.title, it.subtitle, it.habitType.name, it.colorArgb, it.iconName, it.reminderTime, it.isReminderEnabled)
        }
        val journals = journalRepository.getAllJournalEntries().first().map {
            JournalExport(it.date.toString(), it.content, it.mood.name, it.isCompleted)
        }
        
        val exportData = ExportData(habits, journals)
        return Json.encodeToString(exportData)
    }
}
