package com.example.myhabittrackerapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhabittrackerapp.data.repository.JournalRepository
import com.example.myhabittrackerapp.model.JournalEntryEntity
import com.example.myhabittrackerapp.model.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class JournalScreenViewModel @Inject constructor(
    private val repository: JournalRepository
) : ViewModel() {

    private val calendar = Calendar.getInstance()
    val today: Date = calendar.time

    var currentEntry by mutableStateOf("")
    var selectedMood by mutableStateOf<Mood?>(null)

    private val _journalEntries = MutableStateFlow<List<JournalEntryEntity>>(emptyList())
    val journalEntries: StateFlow<List<JournalEntryEntity>> = _journalEntries.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadEntries()
    }

    private fun loadEntries() {
        viewModelScope.launch {
            repository.getAllEntries().collect { entries ->
                _journalEntries.value = entries
                _isLoading.value = false
            }
        }
    }

    fun save() {
        if (currentEntry.isNotBlank() && selectedMood != null) {
            viewModelScope.launch {
                val entry = JournalEntryEntity(
                    date = today,
                    content = currentEntry,
                    mood = selectedMood!!,
                    lastEdited = null
                )
                repository.insertEntry(entry)
                clearInputs()
            }
        }
    }

    fun updateEntry(entry: JournalEntryEntity) {
        viewModelScope.launch {
            repository.updateEntry(entry)
        }
    }

    fun deleteEntry(entry: JournalEntryEntity) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }

    private fun clearInputs() {
        currentEntry = ""
        selectedMood = null
    }

    // Helper function to format date for display
    fun formatDateForDisplay(date: Date): String {
        val formatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        return formatter.format(date)
    }

    // For sample data (remove this in production)
    suspend fun addSampleData() {
        val calendar = Calendar.getInstance()

        val sampleEntries = listOf(
            JournalEntryEntity(
                date = calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time,
                content = "Took a long walk in the park today. The autumn leaves are finally starting to turn.",
                mood = Mood.Satisfied,
                lastEdited = "14h ago"
            ),
            JournalEntryEntity(
                date = calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time,
                content = "Celebrated Sarah's birthday! We went to that new Italian place downtown.",
                mood = Mood.VerySatisfied
            )
        )

        sampleEntries.forEach { repository.insertEntry(it) }
    }
}