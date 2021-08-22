package com.mrezanasirloo.dott.app

import android.app.Application
import com.mrezanasirloo.core.task.StartupTask
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var tasks: MutableSet<StartupTask>

    override fun onCreate() {
        super.onCreate()

        tasks.forEach { task -> task.run() }
    }
}