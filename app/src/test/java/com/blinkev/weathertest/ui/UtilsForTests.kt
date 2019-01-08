package com.blinkev.weathertest.ui

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

fun prepareRxSchedulers() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
}

fun resetRxSchedulers() {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(null)
    RxJavaPlugins.setIoSchedulerHandler(null)
}