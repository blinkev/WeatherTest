package com.blinkev.weathertest.ui.screens.weather.item.mapper

import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.ui.screens.weather.item.DayHeaderItem
import com.blinkev.weathertest.ui.screens.weather.item.HourWeatherItem
import com.blinkev.weathertest.ui.screens.weather.item.formatter.DayFormatter
import com.blinkev.weathertest.ui.screens.weather.item.formatter.HourFormatter
import com.blinkev.weathertest.ui.screens.weather.item.formatter.TemperatureFormatter
import com.github.nitrico.lastadapter.StableId
import java.util.*
import javax.inject.Inject

class WeatherMapperImpl @Inject constructor(
        private val dayFormatter: DayFormatter,
        private val hourFormatter: HourFormatter,
        private val temperatureFormatter: TemperatureFormatter
) : WeatherMapper {

    override fun map(entities: List<CityWeather>): List<StableId> = entities
            .flatMap { dateWeather ->
                listOf(createDayHeaderItem(dateWeather.date)) +
                dateWeather.hours.map { hourDetails ->
                    createHourWeatherItem(hourDetails)
                }
            }

    private fun createDayHeaderItem(day: Date) = DayHeaderItem(dayFormatter.format(day))

    private fun createHourWeatherItem(hourDetails: CityWeather.HourDetails) = HourWeatherItem(
            date = hourDetails.hour,
            hour = hourFormatter.format(hourDetails.hour),
            temperature = temperatureFormatter.format(hourDetails.temperature),
            description = hourDetails.description,
            iconUrl = hourDetails.iconUrl
    )
}