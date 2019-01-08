package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.mock
import com.blinkev.weathertest.domain.entity.CityWeather
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class GetCityWeatherRespMapperImplTest {

    private lateinit var dateMapper: DateMapper
    private lateinit var hourMapper: HourWeatherDtoToEntityMapper

    private lateinit var mapper: GetCityWeatherRespMapper

    @Before
    fun setUp() {
        dateMapper = mock {}
        hourMapper = mock {}

        mapper = GetCityWeatherRespMapperImpl(
            dateMapper = dateMapper,
            hourMapper = hourMapper
        )
    }

    @Test
    fun `map() - empty weather data should be mapped to empty entity list`() {
        val responseWithEmptyWeatherData =
            GetCityWeatherResp.mock(GetCityWeatherResp.DataDto.mock(weather = emptyList()))
        assertEquals(emptyList<CityWeather>(), mapper.map(responseWithEmptyWeatherData))
    }

    @Test
    fun `map() - weather data with empty hours data should be omitted`() {
        val correctWeatherDataDateDto = "correct weather data date"
        val mappedCorrectWeatherDataDate = Date(1)
        val correctWeatherData = GetCityWeatherResp.DayWeatherDto.mock(
            date = correctWeatherDataDateDto,
            hourly = listOf(GetCityWeatherResp.HourWeatherDto.mock())
        )
        whenever(dateMapper.map(correctWeatherDataDateDto)).thenReturn(mappedCorrectWeatherDataDate)
        whenever(hourMapper.map(day = any(), dto = any())).thenReturn(CityWeather.HourDetails.mock())

        val incorrectWeatherData = GetCityWeatherResp.DayWeatherDto.mock(
            hourly = emptyList()
        )

        val responseWithIncorrectAndCorrectWeatherData = GetCityWeatherResp.mock(
            GetCityWeatherResp.DataDto.mock(
                listOf(incorrectWeatherData, correctWeatherData)
            )
        )

        mapper.map(responseWithIncorrectAndCorrectWeatherData).apply {
            assertEquals(1, size)
            assertEquals(mappedCorrectWeatherDataDate, get(0).date)
        }
    }

    @Test
    fun `map() - we should map each day and hours in that day to corresponding entity with mapped date and hour`() {
        val mappedDay1Date = Date(1)
        val mappedDay2Date = Date(2)
        whenever(dateMapper.map(any())).thenReturn(mappedDay1Date, mappedDay2Date)

        val mappedDay1Hour = CityWeather.HourDetails.mock(hour = Date(100))
        val mappedDay2Hour = CityWeather.HourDetails.mock(hour = Date(200))
        whenever(hourMapper.map(day = any(), dto = any())).thenReturn(mappedDay1Hour, mappedDay2Hour)

        val day1Dto = GetCityWeatherResp.DayWeatherDto.mock(hourly = listOf(GetCityWeatherResp.HourWeatherDto.mock()))
        val day2Dto = GetCityWeatherResp.DayWeatherDto.mock(hourly = listOf(GetCityWeatherResp.HourWeatherDto.mock()))
        val responseWithTwoDaysWithOneHourInEach = GetCityWeatherResp.mock(
            GetCityWeatherResp.DataDto.mock(
                listOf(day1Dto, day2Dto)
            )
        )

        mapper.map(responseWithTwoDaysWithOneHourInEach).apply {
            assertEquals(2, size)

            val mappedDay1 = get(0)
            assertEquals(mappedDay1Date, mappedDay1.date)
            assertEquals(1, mappedDay1.hours.size)
            assertEquals(mappedDay1Hour, mappedDay1.hours[0])

            val mappedDay2 = get(1)
            assertEquals(mappedDay2Date, mappedDay2.date)
            assertEquals(1, mappedDay2.hours.size)
            assertEquals(mappedDay2Hour, mappedDay2.hours[0])
        }
    }
}