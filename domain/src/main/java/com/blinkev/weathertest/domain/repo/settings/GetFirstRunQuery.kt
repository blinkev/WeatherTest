package com.blinkev.weathertest.domain.repo.settings

import io.reactivex.Observable

interface GetFirstRunQuery {
    fun get(): Observable<Boolean>
}