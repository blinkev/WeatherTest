package com.blinkev.weathertest.ui.screens.weather.item.mapper

import com.blinkev.weathertest.domain.entity.CityWeather
import com.github.nitrico.lastadapter.StableId

interface WeatherMapper {
    fun map(entities: List<CityWeather>): List<StableId>
}