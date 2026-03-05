package com.example.myhabittrackerapp.model

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class JournalRepository(private val journalDao: JournalDao) {
    fun getAllJournalEntries(): Flow<List<JournalEntry>> =
        journalDao.getAllJournalEntries()

    fun getJournalEntriesForHabit(habitId: Long): Flow<List<JournalEntry>> =
        journalDao.getJournalEntriesForHabit(habitId)

    fun getJournalEntriesForDate(date: LocalDate): Flow<List<JournalEntry>> =
        journalDao.getJournalEntriesForDate(date)

    suspend fun getJournalEntryForHabitAndDate(habitId: Long, date: LocalDate): JournalEntry? =
        journalDao.getJournalEntryForHabitAndDate(habitId, date)

    suspend fun insertJournalEntry(entry: JournalEntry): Long =
        journalDao.insertJournalEntry(entry)

    suspend fun updateJournalEntry(entry: JournalEntry) =
        journalDao.updateJournalEntry(entry)

    suspend fun deleteJournalEntry(entry: JournalEntry) =
        journalDao.deleteJournalEntry(entry)
}
