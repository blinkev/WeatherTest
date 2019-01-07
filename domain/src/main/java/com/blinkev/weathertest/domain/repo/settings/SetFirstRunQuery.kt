package com.blinkev.weathertest.domain.repo.settings

import io.reactivex.Observable

interface SetFirstRunQuery {
    fun set(isFirstRun: Boolean): Observable<Unit>
}