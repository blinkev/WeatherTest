package com.blinkev.weathertest.domain.repo.settings

import com.blinkev.weathertest.domain.assertLoadingAndData
import com.blinkev.weathertest.domain.assertLoadingAndError
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.toDataStatus
import com.blinkev.weathertest.domain.toObservable
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SettingsRepoImplTest {

    private lateinit var getQuery: GetFirstRunQuery
    private lateinit var setQuery: SetFirstRunQuery

    private lateinit var repo: SettingsRepo

    @Before
    fun setUp() {
        getQuery = mock {}
        setQuery = mock {}

        repo = SettingsRepoImpl(
            getQuery = getQuery,
            setQuery = setQuery
        )
    }

    @Test
    fun `getFirstRunSetting() - when repo succeeds getting first run setting we should return that setting in data status`() {
        val data = true
        whenever(getQuery.get()).thenReturn(data.toObservable())

        repo.getFirstRunSetting().test()
            .assertValue(data.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `getFirstRunSetting() - when repo fails getting first run setting we should return error in data status`() {
        val error = Throwable()
        whenever(getQuery.get()).thenReturn(error.toObservable())

        repo.getFirstRunSetting().test()
            .assertValue(error.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `setFirstRunSettings() - when repo succeeds setting first run setting we should return success unit in data status`() {
        whenever(setQuery.set(any())).thenReturn(Unit.toObservable())

        repo.setFirstRunSettings(true).test()
            .assertValue(Unit.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `setFirstRunSettings() - when repo fails setting first run setting we should return error in data status`() {
        val error = Throwable()
        whenever(setQuery.set(any())).thenReturn(error.toObservable())

        repo.setFirstRunSettings(true).test()
            .assertValue(error.toDataStatus())
            .assertNoErrors()
    }
}