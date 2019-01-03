package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City

interface CitiesUiMapper {
    fun map(entities: List<City>): List<CityItem>
}