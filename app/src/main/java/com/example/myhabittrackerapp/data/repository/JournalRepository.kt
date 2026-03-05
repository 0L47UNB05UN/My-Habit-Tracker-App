package com.example.myhabittrackerapp.data.repository


import com.example.myhabittrackerapp.data.JournalEntryDao
import com.example.myhabittrackerapp.model.JournalEntryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JournalRepository @Inject constructor(
    private val dao: JournalEntryDao
) {
    fun getAllEntries(): Flow<List<JournalEntryEntity>> = dao.getAllEntries()
    suspend fun getEntryById(id: Long): JournalEntryEntity? = dao.getEntryById(id)
    suspend fun insertEntry(entry: JournalEntryEntity): Long = dao.insertEntry(entry)
    suspend fun updateEntry(entry: JournalEntryEntity) = dao.updateEntry(entry)
    suspend fun deleteEntry(entry: JournalEntryEntity) = dao.deleteEntry(entry)
    suspend fun hasEntryForDate(date: java.util.Date): Boolean =
        dao.hasEntryForDate(date) > 0
}