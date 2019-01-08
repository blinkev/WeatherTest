package com.blinkev.weathertest.ui.screens.weather.item.mapper

import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.ui.mock
import com.blinkev.weathertest.ui.screens.weather.item.DayHeaderItem
import com.blinkev.weathertest.ui.screens.weather.item.HourWeatherItem
import com.blinkev.weathertest.ui.screens.weather.item.formatter.DayFormatter
import com.blinkev.weathertest.ui.screens.weather.item.formatter.HourFormatter
import com.blinkev.weathertest.ui.screens.weather.item.formatter.TemperatureFormatter
import com.github.nitrico.lastadapter.StableId
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class WeatherMapperImplTest {

    private lateinit var dayFormatter: DayFormatter
    private lateinit var hourFormatter: HourFormatter
    private lateinit var temperatureFormatter: TemperatureFormatter

    private val formattedDay1 = "formatted day 1"
    private val formattedDay2 = "formatted day 2"
    private val formattedHour1 = "formatted hour 1"
    private val formattedHour2 = "formatted hour 2"
    private val formattedTemperature = "formatted temperature"

    private lateinit var mapper: WeatherMapper

    @Before
    fun setUp() {
        dayFormatter = mock {
            on { format(day = any(), today = any()) }.thenReturn(formattedDay1, formattedDay2)
        }
        hourFormatter = mock {
            on { format(any()) }.thenReturn(formattedHour1, formattedHour2)
        }
        temperatureFormatter = mock {
            on { format(any()) }.thenReturn(formattedTemperature)
        }

        mapper = WeatherMapperImpl(
            dayFormatter = dayFormatter,
            hourFormatter = hourFormatter,
            temperatureFormatter = temperatureFormatter
        )
    }

    @Test
    fun `map() - empty entity list should be mapped to empty items list`() {
        assertEquals(emptyList<StableId>(), mapper.map(emptyList()))
    }

    @Test
    fun `map() - two different days with one hour in each should be mapped to two header items with one hour item in each`() {
        val entityList = listOf(
            CityWeather.mock(hours = listOf(CityWeather.HourDetails.mock())),
            CityWeather.mock(hours = listOf(CityWeather.HourDetails.mock()))
        )

        mapper.map(entityList).apply {
            assertEquals(4, size)

            assertEquals(formattedDay1, (get(0) as? DayHeaderItem)?.text)
            assertEquals(formattedHour1, (get(1) as? HourWeatherItem)?.hour)

            assertEquals(formattedDay2, (get(2) as? DayHeaderItem)?.text)
            assertEquals(formattedHour2, (get(3) as? HourWeatherItem)?.hour)
        }
    }
}