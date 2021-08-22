package com.mrezanasirloo.core.di

import android.os.Looper
import com.mrezanasirloo.core.rx.AppSchedulers
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
object RxJavaModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Reusable
    fun provideSchedulers() = AppSchedulers(
        Schedulers.io(),
        AndroidSchedulers.from(Looper.getMainLooper(), true)
    )
}