package com.example.myhabittrackerapp.model

import android.content.Context
import androidx.room.Room

interface AppContainer {
    val habitRepository: HabitRepository
    val journalRepository: JournalRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "habit_tracker_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    override val habitRepository: HabitRepository by lazy {
        HabitRepository(database.habitDao())
    }

    override val journalRepository: JournalRepository by lazy {
        JournalRepository(database.journalDao())
    }
}
