package com.example.myhabittrackerapp.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhabittrackerapp.model.JournalEntryEntity

@Database(
    entities = [JournalEntryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalEntryDao(): JournalEntryDao

    companion object {
        const val DATABASE_NAME = "journal_database"
    }
}