package com.blinkev.weathertest.ui

import com.blinkev.weathertest.domain.DataStatus
import io.reactivex.Observable
import net.bytebuddy.implementation.bytecode.Throw

fun <T> T.toObservableDataStatus(): Observable<DataStatus<T>> = Observable.just(this.toDataStatus())

fun <T> Throwable.toObservableDataStatus(): Observable<DataStatus<T>> = Observable.just(DataStatus.error(this))

fun <T> T.toDataStatus(): DataStatus<T> = DataStatus.data(this)

fun <T> Throwable.toDataStatus(): DataStatus<T> = DataStatus.error(this)