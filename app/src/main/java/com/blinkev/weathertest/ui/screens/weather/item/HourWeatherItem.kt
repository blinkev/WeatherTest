package com.blinkev.weathertest.ui.screens.weather.item

import com.blinkev.weathertest.domain.entity.City
import com.github.nitrico.lastadapter.StableId
import java.util.*

data class HourWeatherItem(
        val date: Date,
        val hour: String,
        val temperature: String,
        val description: String,
        val iconUrl: String
) : StableId {

    override val stableId: Long = hour.hashCode() + date.time
}