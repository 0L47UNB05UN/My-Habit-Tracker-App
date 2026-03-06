package com.example.myhabittrackerapp.di

import android.content.Context
import androidx.room.Room
import com.example.myhabittrackerapp.model.AppDatabase
import com.example.myhabittrackerapp.model.HabitDao
import com.example.myhabittrackerapp.model.HabitRepository
import com.example.myhabittrackerapp.model.JournalDao
import com.example.myhabittrackerapp.model.JournalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "habit_tracker_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideHabitDao(database: AppDatabase): HabitDao = database.habitDao()

    @Provides
    fun provideJournalDao(database: AppDatabase): JournalDao = database.journalDao()

    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository = HabitRepository(habitDao)

    @Singleton
    @Provides
    fun provideJournalRepository(journalDao: JournalDao): JournalRepository = JournalRepository(journalDao)
}
