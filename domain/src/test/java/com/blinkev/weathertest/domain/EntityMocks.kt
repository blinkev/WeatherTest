package com.blinkev.weathertest.domain

import com.blinkev.weathertest.domain.entity.CityWeather
import java.util.*

fun CityWeather.Companion.mock(
    date: Date = Date(1),
    hours: List<CityWeather.HourDetails> = emptyList()
) = CityWeather(
    date = date,
    hours = hours
)