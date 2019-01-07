package com.blinkev.weathertest.data.query.settings

import android.content.Context
import com.blinkev.weathertest.data.query.city.CityRepoQueriesImpl
import com.blinkev.weathertest.domain.exception.StorageException
import com.blinkev.weathertest.domain.util.empty
import io.reactivex.Observable
import javax.inject.Inject

class SettingsRepoQueriesImpl @Inject constructor(
    private val appContext: Context
) : SettingsRepoQueries {

    companion object {
        private const val SETTINGS_PREF_NAME = "SETTINGS_PREF_NAME"
        private const val FIRST_RUN = "FIRST_RUN"
        private const val FIRST_RUN_DEFAULT = true
    }

    override fun get(): Observable<Boolean> = Observable.fromCallable {
        synchronized(this@SettingsRepoQueriesImpl) {
            appContext
                .getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE)
                .getBoolean(FIRST_RUN, FIRST_RUN_DEFAULT)
        }
    }

    override fun set(isFirstRun: Boolean): Observable<Unit> = Observable.fromCallable {
        synchronized(this@SettingsRepoQueriesImpl) {
            val result = appContext
                .getSharedPreferences(SETTINGS_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(FIRST_RUN, isFirstRun)
                .commit()

            if (!result) throw StorageException()
        }
    }
}