package com.example.myhabittrackerapp.model

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Int?): LocalDate? {
        return value?.let { LocalDate.fromEpochDays(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Int? {
        return date?.toEpochDays()
    }

    @TypeConverter
    fun fromHabitType(type: HabitType): String {
        return type.name
    }

    @TypeConverter
    fun toHabitType(value: String): HabitType {
        return HabitType.valueOf(value)
    }

    @TypeConverter
    fun fromMood(mood: Mood): String {
        return mood.name
    }

    @TypeConverter
    fun toMood(value: String): Mood {
        return Mood.valueOf(value)
    }
}
