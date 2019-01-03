package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City
import javax.inject.Inject

class CityEntityMapperImpl @Inject constructor() : CityEntityMapper {

    override fun map(cityName: String) = City(cityName)
}