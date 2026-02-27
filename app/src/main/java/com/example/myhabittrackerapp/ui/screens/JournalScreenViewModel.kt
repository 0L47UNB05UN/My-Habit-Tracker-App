package com.example.myhabittrackerapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myhabittrackerapp.model.JournalEntry
import com.example.myhabittrackerapp.model.Mood
import java.time.LocalDate

class JournalScreenViewModel: ViewModel(){
    val today: LocalDate = LocalDate.now()
    var currentEntry by  mutableStateOf("")
    var selectedMood by  mutableStateOf<Mood?>(null)
    val journalEntries =  mutableStateListOf(
        JournalEntry(
            date = today.minusDays(1),
            content = "Took a long walk in the park today. The autumn leaves are finally starting to turn. Spent the evening reading by the window. Feeling much more rested than yesterday.",
            mood = Mood.Satisfied,
            lastEdited = "14h ago"
        ),
        JournalEntry(
            date = today.minusDays(2),
            content = "Celebrated Sarah's birthday! We went to that new Italian place downtown. The pasta was incredible, but the company was even better. Truly a day to remember.",
            mood = Mood.VerySatisfied
        )
    )

    fun save(){
        journalEntries.add(
            0,
            JournalEntry(
                date = today.minusDays(1),
                content = currentEntry,
                mood = selectedMood!!
            )
        )
        currentEntry = ""
        selectedMood = null
    }
}