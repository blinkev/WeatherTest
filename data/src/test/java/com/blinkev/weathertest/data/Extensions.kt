package com.blinkev.weathertest.data

import com.blinkev.weathertest.domain.DataStatus
import io.reactivex.Observable
import net.bytebuddy.implementation.bytecode.Throw

fun <T> T.toObservable(): Observable<T> = Observable.just(this)

fun <T> Throwable.toObservable(): Observable<T> = Observable.error(this)