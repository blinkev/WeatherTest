package com.blinkev.weathertest.ui.screens.cities

import com.blinkev.weathertest.domain.entity.City

interface CitiesRouter {
    fun routeToCityWeather(city: City)
}