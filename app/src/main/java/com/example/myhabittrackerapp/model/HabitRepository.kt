package com.example.myhabittrackerapp.model

import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: Flow<List<Habits>> = habitDao.getAllHabits()

    suspend fun getHabitById(id: Long): Habits? = habitDao.getHabitById(id)

    suspend fun insertHabit(habit: Habits): Long = habitDao.insertHabit(habit)

    suspend fun updateHabit(habit: Habits) = habitDao.updateHabit(habit)

    suspend fun deleteHabit(habit: Habits) = habitDao.deleteHabit(habit)
}
