package com.example.myhabittrackerapp.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class HabitRepository(
    private val habitDao: HabitDao,
    private val journalDao: JournalDao
) {
    val allHabits: Flow<List<Habits>> = habitDao.getAllHabits()

    suspend fun getHabitById(id: Long): Habits? = habitDao.getHabitById(id)

    suspend fun insertHabit(habit: Habits): Long = habitDao.insertHabit(habit)

    suspend fun updateHabit(habit: Habits) = habitDao.updateHabit(habit)

    suspend fun deleteHabit(habit: Habits) = habitDao.deleteHabit(habit)

    /**
     * Calculates the current streak for a habit by checking journal entries backwards from yesterday.
     */
    suspend fun getStreakForHabit(habitId: Long): Int {
        val allEntries = journalDao.getJournalEntriesForHabit(habitId).first()
        if (allEntries.isEmpty()) return 0

        var streak = 0
        val timeZone = TimeZone.currentSystemDefault()
        var currentDate = Clock.System.now().toLocalDateTime(timeZone).date
        
        // Start checking from today or yesterday
        // If they already completed it today, that counts towards the current streak
        val hasCompletedToday = allEntries.any { it.date == currentDate && it.isCompleted }
        if (hasCompletedToday) {
            streak++
        }
        
        // Now check backwards from yesterday
        var checkDate = currentDate.minus(1, DateTimeUnit.DAY)
        
        while (true) {
            val entry = allEntries.find { it.date == checkDate }
            if (entry != null && entry.isCompleted) {
                streak++
                checkDate = checkDate.minus(1, DateTimeUnit.DAY)
            } else {
                break // Streak broken
            }
        }
        
        return streak
    }
}
