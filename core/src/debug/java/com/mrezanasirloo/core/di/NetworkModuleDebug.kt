package com.mrezanasirloo.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY

/**
 * This module helps us to provide dependencies that are only needed in debug build
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleDebug {

    @IntoSet
    @Provides
    fun provideNetworkLogger(): Interceptor {
        return HttpLoggingInterceptor().setLevel(BODY)
    }
}