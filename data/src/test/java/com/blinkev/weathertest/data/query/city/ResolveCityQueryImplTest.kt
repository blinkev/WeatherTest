package com.blinkev.weathertest.data.query.city

import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.data.mock
import com.blinkev.weathertest.data.query.city.mapper.LocationMapper
import com.blinkev.weathertest.data.query.city.mapper.ResolveCityNameRespMapper
import com.blinkev.weathertest.data.toObservable
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.repo.city.ResolveCityQuery
import com.blinkev.weathertest.domain.util.empty
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ResolveCityQueryImplTest {

    private lateinit var api: WeatherApi
    private lateinit var locationMapper: LocationMapper
    private lateinit var respMapper: ResolveCityNameRespMapper

    private val lat = 100500.0
    private val lon = 200500.0

    private lateinit var query: ResolveCityQuery

    @Before
    fun setUp() {
        api = mock {
            on { resolveCityName(location = any(), apiKey = any()) }.thenReturn(Observable.never())
        }
        locationMapper = mock {
            on { map(any(), any()) }.thenReturn(String.empty())
        }
        respMapper = mock {}

        query = ResolveCityQueryImpl(
            api = api,
            locationMapper = locationMapper,
            resolveCityRespMapper = respMapper
        )
    }

    @Test
    fun `resolve() - should use mapped location to resolve city name`() {
        val mappedLocation = "100500.0,200500.0"
        whenever(locationMapper.map(lat, lon)).thenReturn(mappedLocation)

        query.resolve(lat, lon).test()

        verify(api).resolveCityName(location = eq(mappedLocation), apiKey = any())
    }

    @Test
    fun `resolve() - when api succeeds resolving city name we should return city name from response mapper`() {
        val resp = ResolveCityNameResp.mock()
        whenever(api.resolveCityName(location = any(), apiKey = any())).thenReturn(resp.toObservable())
        val mappedResp = City("city")
        whenever(respMapper.map(resp)).thenReturn(mappedResp)

        query.resolve(lat, lon).test()
            .assertValue(mappedResp)
            .assertNoErrors()
    }

    @Test
    fun `resolve() - when api fails resolving city name we should return error`() {
        val error = Throwable()
        whenever(api.resolveCityName(location = any(), apiKey = any())).thenReturn(error.toObservable())

        query.resolve(lat, lon).test()
            .assertError(error)
            .assertNoValues()
    }
}