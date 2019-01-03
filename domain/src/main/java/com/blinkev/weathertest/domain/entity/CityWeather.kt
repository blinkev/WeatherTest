package com.blinkev.weathertest.domain.entity

import java.util.*

data class CityWeather(
    val date: Date,
    val hours: List<HourDetails>
) {
    data class HourDetails(
        val hour: Date,
        val temperature: Int,
        val description: String,
        val iconUrl: String
    )
}