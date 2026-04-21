package com.example.myhabittrackerapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myhabittrackerapp.MainActivity
import com.example.myhabittrackerapp.model.HabitRepository
import com.example.myhabittrackerapp.model.JournalRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@HiltWorker
class HabitReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val journalRepository: JournalRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("HabitReminderWorker", "Background check started...")
        val habits = habitRepository.allHabits.first()
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        
        Log.d("HabitReminderWorker", "Checking ${habits.size} habits. Current time: ${currentTime.hour}:${currentTime.minute}")

        habits.forEach { habit ->
            if (habit.isReminderEnabled && habit.reminderTime != null) {
                val timeParts = habit.reminderTime.split(":")
                val reminderHour = timeParts[0].toIntOrNull() ?: 0
                val reminderMinute = timeParts[1].toIntOrNull() ?: 0

                Log.d("HabitReminderWorker", "Habit: ${habit.title}, Reminder: $reminderHour:$reminderMinute")

                // If current time is past reminder time
                if (currentTime.hour > reminderHour || (currentTime.hour == reminderHour && currentTime.minute >= reminderMinute)) {
                    val entry = journalRepository.getJournalEntryForHabitAndDate(habit.id, today)
                    if (entry == null) {
                        Log.d("HabitReminderWorker", "Triggering notification for: ${habit.title}")
                        showNotification(habit.id, habit.title)
                    } else {
                        Log.d("HabitReminderWorker", "Journal already exists for today for: ${habit.title}")
                    }
                }
            }
        }

        return Result.success()
    }

    private fun showNotification(habitId: Long, habitTitle: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "habit_reminders"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Habit Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Daily reminders to log your habit progress"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("HABIT_ID", habitId)
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, habitId.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Daily Habit Reminder")
            .setContentText("Don't forget to log your progress for: $habitTitle")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(habitId.hashCode(), notification)
    }
}
