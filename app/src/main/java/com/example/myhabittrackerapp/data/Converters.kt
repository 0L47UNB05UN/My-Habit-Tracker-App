package com.example.myhabittrackerapp.data

import androidx.room.TypeConverter
import com.example.myhabittrackerapp.model.Mood
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromMoodString(value: String): Mood {
        return Mood.valueOf(value)
    }

    @TypeConverter
    fun moodToString(mood: Mood): String {
        return mood.name
    }
}