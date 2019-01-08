package com.blinkev.weathertest.domain.repo.city

import com.blinkev.weathertest.domain.*
import com.blinkev.weathertest.domain.entity.City
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class CityRepoImplTest {

    private lateinit var getQuery: GetCityListQuery
    private lateinit var addQuery: AddCityQuery
    private lateinit var removeQuery: RemoveCityQuery
    private lateinit var resolveQuery: ResolveCityQuery

    private val testLat = 100500.0
    private val testLon = 200500.0

    private lateinit var repo: CityRepo

    @Before
    fun setUp() {
        getQuery = mock {}
        addQuery = mock {}
        removeQuery = mock {}
        resolveQuery = mock {}

        repo = CityRepoImpl(
            getQuery = getQuery,
            addQuery = addQuery,
            removeQuery = removeQuery,
            resolveQuery = resolveQuery
        )
    }

    @Test
    fun `getAll() - when repo succeeds getting all cities we should return them in data status`() {
        val data = City("city")
        whenever(getQuery.get()).thenReturn(listOf(data).toObservable())

        repo.getAll().test()
            .assertValue(listOf(data).toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `getAll() - when repo fails getting all cities we should return error in data status`() {
        val error = Throwable()
        whenever(getQuery.get()).thenReturn(error.toObservable())

        repo.getAll().test()
            .assertValue(error.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `addCity() - when repo succeeds adding new city we should return success unit in data status`() {
        whenever(addQuery.add(any())).thenReturn(Unit.toObservable())

        repo.addCity(City("city")).test()
            .assertValue(Unit.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `addCity() - when repo fails adding new city we should return error in data status`() {
        val error = Throwable()
        whenever(addQuery.add(any())).thenReturn(error.toObservable())

        repo.addCity(City("city")).test()
            .assertValue(error.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `removeCity() - when repo succeeds removing city we should return success unit in data status`() {
        whenever(removeQuery.remove(any())).thenReturn(Unit.toObservable())

        repo.removeCity(City("city")).test()
            .assertValue(Unit.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `removeCity() - when repo fails removing city we should return error in data status`() {
        val error = Throwable()
        whenever(removeQuery.remove(any())).thenReturn(error.toObservable())

        repo.removeCity(City("city")).test()
            .assertValue(error.toDataStatus())
            .assertNoErrors()
    }

    @Test
    fun `resolveCity() - when repo succeeds resolving city we should return loading and that city in data status`() {
        val data = City("city")
        whenever(resolveQuery.resolve(lat = any(), lon = any())).thenReturn(data.toObservable())

        repo.resolveCity(testLat, testLon).test()
            .assertLoadingAndData(data)
            .assertNoErrors()
    }

    @Test
    fun `resolveCity() - when repo fails resolving city we should return loading and error in data status`() {
        val error = Throwable()
        whenever(resolveQuery.resolve(lat = any(), lon = any())).thenReturn(error.toObservable())

        repo.resolveCity(testLat, testLon).test()
            .assertLoadingAndError(error)
    }
}