package com.blinkev.weathertest.domain.util

import android.provider.ContactsContract
import com.blinkev.weathertest.domain.DataStatus
import io.reactivex.Observable

fun <T, K> DataStatus.Loading<T>.changeGeneric(): DataStatus<K> = DataStatus.loading()

fun <T, K> DataStatus.Error<T>.changeGeneric(): DataStatus<K> = DataStatus.error(this.error)

fun <T> Observable<T>.toDataStatus(): Observable<DataStatus<T>> = this
    .map { DataStatus.data(it) }
    .onErrorResumeNext { t: Throwable -> Observable.just(DataStatus.error(t)) }

fun <T> Observable<T>.toDataStatusWithLoading(): Observable<DataStatus<T>> = this
    .toDataStatus()
    .startWith(DataStatus.loading())

fun <T, R> Observable<DataStatus<T>>.mapData(lambda: (T) -> R): Observable<DataStatus<R>> =
    this.map {
        when (it) {
            is DataStatus.Loading -> DataStatus.Loading<R>()
            is DataStatus.Data -> DataStatus.Data(lambda(it.data))
            is DataStatus.Error -> DataStatus.error(it.error)
        }
    }

fun <T, R> Observable<DataStatus<T>>.flatMapData(lambda: (T) -> Observable<DataStatus<R>>): Observable<DataStatus<R>> =
    this.flatMap {
        when (it) {
            is DataStatus.Loading -> Observable.just<DataStatus<R>>(DataStatus.Loading())
            is DataStatus.Data -> lambda(it.data)
            is DataStatus.Error -> Observable.just<DataStatus<R>>(DataStatus.error(it.error))
        }
    }

fun String.Companion.empty() = ""