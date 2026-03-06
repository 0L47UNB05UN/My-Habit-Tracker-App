package com.example.myhabittrackerapp.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myhabittrackerapp.data.HabitDao
import com.example.myhabittrackerapp.model.HabitEntity
import com.example.myhabittrackerapp.model.Habits
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {

    val allHabits: Flow<List<Habits>> = habitDao.getAllHabits()
        .map { entities -> entities.map { it.toDomain() } }

    suspend fun getHabitById(id: String): Habits? {
        return habitDao.getHabitById(id)?.toDomain()
    }

    suspend fun insertHabit(habit: Habits) {
        habitDao.insertHabit(habit.toEntity())
    }

    suspend fun updateHabit(habit: Habits) {
        habitDao.updateHabit(habit.toEntity())
    }

    suspend fun deleteHabit(habit: Habits) {
        habitDao.deleteHabit(habit.toEntity())
    }

    suspend fun deleteAllHabits() {
        habitDao.deleteAllHabits()
    }

    // Extension functions for mapping
    private fun Habits.toEntity(): HabitEntity = HabitEntity(
        id = this.id ?: java.util.UUID.randomUUID().toString(),
        title = this.title,
        subtitle = this.subtitle,
        habitType = this.habitType,
        iconName = this.icon.name, // Store icon name
        colorHex = this.color.value.toLong() // Store color as Long
    )

    private fun HabitEntity.toDomain(): Habits = Habits(
        id = this.id,
        title = this.title,
        subtitle = this.subtitle,
        habitType = this.habitType,
        icon = getIconFromName(this.iconName),
        color = androidx.compose.ui.graphics.Color(this.colorHex)
    )

    private fun getIconFromName(iconName: String): ImageVector {
        return when (iconName) {
            "WaterDrop" -> Icons.Outlined.WaterDrop
            "SelfImprovement" -> Icons.Outlined.SelfImprovement
            "Fastfood" -> Icons.Outlined.Fastfood
            "Smartphone" -> Icons.Outlined.Smartphone
            else -> Icons.AutoMirrored.Outlined.Help
        }
    }
}