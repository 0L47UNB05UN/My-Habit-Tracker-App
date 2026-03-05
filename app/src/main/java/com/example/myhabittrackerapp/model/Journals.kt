package com.example.myhabittrackerapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

data class JournalEntry(
    val date: Date,
    val content: String,
    val mood: Mood,
    val lastEdited: String? = null
)

enum class Mood(val icon: ImageVector) {
    VerySatisfied(Icons.Filled.SentimentVerySatisfied),
    Satisfied(Icons.Filled.SentimentSatisfied),
    Neutral(Icons.Filled.SentimentNeutral)
}


@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val content: String,
    val mood: Mood,
    val lastEdited: String? = null
)