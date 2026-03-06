package com.example.myhabittrackerapp.di



import android.content.Context
import androidx.room.Room
import com.example.myhabittrackerapp.data.HabitDao
import com.example.myhabittrackerapp.data.JournalDatabase
import com.example.myhabittrackerapp.data.JournalEntryDao
import com.example.myhabittrackerapp.data.repository.HabitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideJournalDatabase(
        @ApplicationContext context: Context
    ): JournalDatabase {
        return Room.databaseBuilder(
            context,
            JournalDatabase::class.java,
            JournalDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDatabase(
        @ApplicationContext context: Context
    ): HabitDatabase {
        return Room.databaseBuilder(
            context,
            HabitDatabase::class.java,
            HabitDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideJournalEntryDao(
        database: JournalDatabase
    ): JournalEntryDao {
        return database.journalEntryDao()
    }

    @Provides
    @Singleton
    fun provideHabitDao(
        database: HabitDatabase
    ): HabitDao {
        return database.habitDao()
    }
}

