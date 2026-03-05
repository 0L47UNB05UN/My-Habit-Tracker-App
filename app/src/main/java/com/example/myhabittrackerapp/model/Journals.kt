package com.example.myhabittrackerapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "journal_entries",
    foreignKeys = [
        ForeignKey(
            entity = Habits::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habitId"])]
)
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val habitId: Long, // Linked to the specific habit
    val date: LocalDate,
    val content: String,
    val mood: Mood,
    val isCompleted: Boolean = true,
    val lastEdited: String? = null
)

enum class Mood {
    VerySatisfied,
    Satisfied,
    Neutral
}
