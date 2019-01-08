package com.blinkev.weathertest.domain.repo.weather

import com.blinkev.weathertest.domain.assertLoadingAndData
import com.blinkev.weathertest.domain.assertLoadingAndError
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.mock
import com.blinkev.weathertest.domain.toObservable
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CityWeatherRepoImplTest {

    private lateinit var query: GetCityWeatherQuery

    private lateinit var repo: CityWeatherRepo

    @Before
    fun setUp() {
        query = mock {}

        repo = CityWeatherRepoImpl(query)
    }

    @Test
    fun `getWeather() - when repo succeeds getting city weather we should return loading and that weather in data status`() {
        val data = CityWeather.mock()
        whenever(query.get(any())).thenReturn(listOf(data).toObservable())

        repo.getWeather(City("city")).test()
            .assertLoadingAndData(listOf(data))
            .assertNoErrors()
    }

    @Test
    fun `getWeather() - when repo fails getting city weather we should return loading and error in data status`() {
        val error = Throwable()
        whenever(query.get(any())).thenReturn(error.toObservable())

        repo.getWeather(City("city")).test()
            .assertLoadingAndError(error)
    }
}