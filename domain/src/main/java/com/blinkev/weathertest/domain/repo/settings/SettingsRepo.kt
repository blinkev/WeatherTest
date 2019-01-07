package com.blinkev.weathertest.domain.repo.settings

import com.blinkev.weathertest.domain.DataStatus
import io.reactivex.Observable

interface SettingsRepo {
    fun getFirstRunSetting(): Observable<DataStatus<Boolean>>
    fun setFirstRunSettings(isFirstRun: Boolean): Observable<DataStatus<Unit>>
}