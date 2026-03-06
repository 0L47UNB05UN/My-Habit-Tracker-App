package com.example.myhabittrackerapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myhabittrackerapp.worker.HabitReminderWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HabitTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        scheduleHabitReminders()
    }

    private fun scheduleHabitReminders() {
        val workRequest = OneTimeWorkRequestBuilder<HabitReminderWorker>().build()

        // Start the recursive chain immediately
        WorkManager.getInstance(this).enqueueUniqueWork(
            "habit_reminder_work_recursive",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}
