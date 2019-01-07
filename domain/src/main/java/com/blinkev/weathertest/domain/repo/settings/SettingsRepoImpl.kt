package com.blinkev.weathertest.domain.repo.settings

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.util.toDataStatus
import io.reactivex.Observable
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val getQuery: GetFirstRunQuery,
    private val setQuery: SetFirstRunQuery
) : SettingsRepo {

    override fun getFirstRunSetting(): Observable<DataStatus<Boolean>> = getQuery.get().toDataStatus()

    override fun setFirstRunSettings(isFirstRun: Boolean): Observable<DataStatus<Unit>> = setQuery
        .set(isFirstRun)
        .toDataStatus()
}