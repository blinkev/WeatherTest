package com.blinkev.weathertest.domain

sealed class DataStatus<T> {
    data class Loading<T> internal constructor(private val hiddenVariable: Boolean = true) : DataStatus<T>()
    data class Data<T> internal constructor(val data: T) : DataStatus<T>()
    data class Error<T> internal constructor(val error: Throwable) : DataStatus<T>()

    companion object {
        fun <T> loading(): DataStatus<T> = Loading()
        fun <T> data(data: T): DataStatus<T> = Data(data)
        fun <T> error(error: Throwable): DataStatus<T> = Error(error)
    }
}