package com.blinkev.weathertest.ui.screens.cities.item

import com.blinkev.weathertest.domain.entity.City
import javax.inject.Inject

class CitiesUiMapperImpl @Inject constructor() : CitiesUiMapper {

    override fun map(entities: List<City>): List<CityItem> =
            entities.map {
                CityItem(
                    name = it.name,
                    entity = it
                )
            }
}