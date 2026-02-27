package com.example.myhabittrackerapp.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate

data class JournalEntry(
    val date: LocalDate,
    val content: String,
    val mood: Mood,
    val lastEdited: String? = null
)

enum class Mood(val icon: ImageVector) {
    VerySatisfied(Icons.Filled.SentimentVerySatisfied),
    Satisfied(Icons.Filled.SentimentSatisfied),
    Neutral(Icons.Filled.SentimentNeutral)
}