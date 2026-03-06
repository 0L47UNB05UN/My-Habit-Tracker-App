package com.example.myhabittrackerapp.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myhabittrackerapp.data.Converters
import com.example.myhabittrackerapp.data.HabitDao
import com.example.myhabittrackerapp.model.HabitEntity

@Database(
    entities = [HabitEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {
        const val DATABASE_NAME = "habit_database"
    }
}