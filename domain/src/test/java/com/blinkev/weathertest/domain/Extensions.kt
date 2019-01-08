package com.blinkev.weathertest.domain

import com.blinkev.weathertest.domain.DataStatus
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import net.bytebuddy.implementation.bytecode.Throw

fun <T> T.toObservable(): Observable<T> = Observable.just(this)

fun <T> Throwable.toObservable(): Observable<T> = Observable.error(this)

fun <T> T.toDataStatus(): DataStatus<T> = DataStatus.data(this)

fun <T> Throwable.toDataStatus(): DataStatus<T> = DataStatus.error(this)

fun <T> TestObserver<DataStatus<T>>.assertFirstValueIsLoading(): TestObserver<DataStatus<T>> =
    assertValueAt(0) { it is DataStatus.Loading<T> }

@Suppress("UnstableApiUsage")
fun <T> TestObserver<DataStatus<T>>.assertLoadingAndData(data: T): TestObserver<DataStatus<T>> =
    this.apply {
        assertValueCount(2)
        assertFirstValueIsLoading()
        assertValueAt(1, data.toDataStatus())
    }

@Suppress("UnstableApiUsage")
fun <T> TestObserver<DataStatus<T>>.assertLoadingAndError(error: Throwable): TestObserver<DataStatus<T>> = this.apply {
    assertValueCount(2)
    assertFirstValueIsLoading()
    assertValueAt(1, error.toDataStatus())
}