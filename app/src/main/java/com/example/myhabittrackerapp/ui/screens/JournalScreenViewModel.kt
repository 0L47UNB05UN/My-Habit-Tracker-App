package com.example.myhabittrackerapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabittrackerapp.model.HabitRepository
import com.example.myhabittrackerapp.model.JournalEntry
import com.example.myhabittrackerapp.model.JournalRepository
import com.example.myhabittrackerapp.model.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class JournalScreenViewModel @Inject constructor(
    private val journalRepository: JournalRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {
    val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var currentEntry by mutableStateOf("")
    var isCompleted by mutableStateOf(true)
    var selectedMood by mutableStateOf<Mood?>(null)
    
    var editingEntryId by mutableStateOf<Long?>(null)
    var editingDate by mutableStateOf<LocalDate?>(null)

    private val _currentHabitId = MutableStateFlow<Long?>(null)
    var currentHabitId: Long?
        get() = _currentHabitId.value
        set(value) { _currentHabitId.value = value }

    val currentHabitName: StateFlow<String> = _currentHabitId.map { id ->
        if (id != null) {
            habitRepository.getHabitById(id)?.title ?: "Daily Journal"
        } else {
            "All Journals"
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "Daily Journal")

    val journalEntries: StateFlow<List<JournalEntry>> = _currentHabitId
        .flatMapLatest { id ->
            if (id != null) {
                journalRepository.getJournalEntriesForHabit(id)
            } else {
                journalRepository.getAllJournalEntries()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun startEditing(entry: JournalEntry) {
        editingEntryId = entry.id
        editingDate = entry.date
        currentEntry = entry.content
        isCompleted = entry.isCompleted
        selectedMood = entry.mood
    }

    fun cancelEditing() {
        editingEntryId = null
        editingDate = null
        currentEntry = ""
        isCompleted = true
        selectedMood = null
    }

    fun save() {
        val habitId = currentHabitId ?: return
        viewModelScope.launch {
            val entry = JournalEntry(
                id = editingEntryId ?: 0L,
                habitId = habitId,
                date = editingDate ?: today,
                content = currentEntry,
                mood = selectedMood ?: Mood.Neutral,
                isCompleted = isCompleted
            )
            journalRepository.insertJournalEntry(entry)
            
            cancelEditing()
        }
    }
}
