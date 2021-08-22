package com.mrezanasirloo.core.rx

import io.reactivex.rxjava3.core.Scheduler

class AppSchedulers(
    val io: Scheduler,
    val main: Scheduler,
) {
}