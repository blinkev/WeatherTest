package com.blinkev.weathertest.ui

import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.util.empty
import java.util.*

fun CityWeather.Companion.mock(
    date: Date = Date(),
    hours: List<CityWeather.HourDetails> = emptyList()
) = CityWeather(
    date = date,
    hours = hours
)

fun CityWeather.HourDetails.Companion.mock(
    hour: Date = Date(),
    temperature: Int = 0,
    description: String = String.empty(),
    iconUrl: String = String.empty()
) = CityWeather.HourDetails(
    hour = hour,
    temperature = temperature,
    description = description,
    iconUrl = iconUrl
)