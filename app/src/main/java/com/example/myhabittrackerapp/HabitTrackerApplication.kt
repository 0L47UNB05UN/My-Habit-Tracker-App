package com.example.myhabittrackerapp

import android.app.Application
import com.example.myhabittrackerapp.model.AppContainer
import com.example.myhabittrackerapp.model.AppDataContainer

class HabitTrackerApplication : Application() {
    /**
     * AppContainer instance used by the rest of the classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
