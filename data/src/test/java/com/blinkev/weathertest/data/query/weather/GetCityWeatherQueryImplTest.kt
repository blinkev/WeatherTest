package com.blinkev.weathertest.data.query.weather

import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.data.mock
import com.blinkev.weathertest.data.query.weather.mapper.GetCityWeatherRespMapper
import com.blinkev.weathertest.data.toObservable
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCityWeatherQueryImplTest {

    private lateinit var api: WeatherApi
    private lateinit var mapper: GetCityWeatherRespMapper

    private lateinit var query: GetCityWeatherQuery

    @Before
    fun setUp() {
        api = mock {
            on { getCityWeather(cityName = any(), apiKey = any()) }.thenReturn(Observable.never())
        }
        mapper = mock {}

        query = GetCityWeatherQueryImpl(
            api = api,
            mapper = mapper
        )
    }

    @Test
    fun `get() - should use city name from entity to get city weather`() {
        val cityName = "city"

        query.get(City(cityName)).test()

        verify(api).getCityWeather(cityName = eq(cityName), apiKey = any())
    }

    @Test
    fun `resolve() - when api succeeds getting city weather we should return weather from response mapper`() {
        val resp = GetCityWeatherResp.mock()
        whenever(api.getCityWeather(cityName = any(), apiKey = any())).thenReturn(resp.toObservable())
        val mappedResp = CityWeather.mock()
        whenever(mapper.map(resp)).thenReturn(listOf(mappedResp))

        query.get(City("city")).test()
            .assertValue(listOf(mappedResp))
            .assertNoErrors()
    }

    @Test
    fun `resolve() - when api fails resolving city name we should return error`() {
        val error = Throwable()
        whenever(api.getCityWeather(cityName = any(), apiKey = any())).thenReturn(error.toObservable())

        query.get(City("city")).test()
            .assertError(error)
            .assertNoValues()
    }
}