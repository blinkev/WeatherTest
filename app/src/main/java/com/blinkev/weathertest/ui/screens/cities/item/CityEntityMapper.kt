package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City

interface CityEntityMapper {
    fun map(cityName: String): City
}