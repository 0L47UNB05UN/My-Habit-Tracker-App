package com.example.myhabittrackerapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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
import java.util.concurrent.TimeUnit

@HiltWorker
class HabitReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val journalRepository: JournalRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val habits = habitRepository.allHabits.first()
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        
        habits.forEach { habit ->
            if (habit.isReminderEnabled && habit.reminderTime != null) {
                val timeParts = habit.reminderTime.split(":")
                val reminderHour = timeParts[0].toInt()
                val reminderMinute = timeParts[1].toInt()

                // If current time is past reminder time
                if (currentTime.hour > reminderHour || (currentTime.hour == reminderHour && currentTime.minute >= reminderMinute)) {
                    // Check if journal entry exists for today
                    val entry = journalRepository.getJournalEntryForHabitAndDate(habit.id, today)
                    if (entry == null) {
                        showNotification(habit.title)
                    }
                }
            }
        }

        // Reschedule itself to run again in 2 minutes (bypassing the 15-min periodic limit)
        val nextWorkRequest = OneTimeWorkRequestBuilder<HabitReminderWorker>()
            .setInitialDelay(2, TimeUnit.MINUTES)
            .build()
        
        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            "habit_reminder_work_recursive",
            ExistingWorkPolicy.REPLACE,
            nextWorkRequest
        )

        return Result.success()
    }

    private fun showNotification(habitTitle: String) {
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
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
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

        notificationManager.notify(habitTitle.hashCode(), notification)
    }
}
