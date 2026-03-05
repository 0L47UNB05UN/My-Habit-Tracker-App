package com.example.myhabittrackerapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    fun getAllJournalEntries(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE habitId = :habitId ORDER BY date DESC")
    fun getJournalEntriesForHabit(habitId: Long): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE date = :date")
    fun getJournalEntriesForDate(date: LocalDate): Flow<List<JournalEntry>>

    @Query("SELECT * FROM journal_entries WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getJournalEntryForHabitAndDate(habitId: Long, date: LocalDate): JournalEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournalEntry(entry: JournalEntry): Long

    @Update
    suspend fun updateJournalEntry(entry: JournalEntry)

    @Delete
    suspend fun deleteJournalEntry(entry: JournalEntry)
}
