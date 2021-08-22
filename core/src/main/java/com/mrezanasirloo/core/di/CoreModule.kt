package com.mrezanasirloo.core.di

import com.mrezanasirloo.core.BuildConfig
import com.mrezanasirloo.core.task.StartupTask
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @IntoSet
    @Provides
    fun provideLoggerTask() = StartupTask {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        /** else init a Logger for logging to crashlytics */
    }
}