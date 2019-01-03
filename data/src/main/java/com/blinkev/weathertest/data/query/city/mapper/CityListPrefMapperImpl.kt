package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.domain.entity.City
import javax.inject.Inject

class CityListPrefMapperImpl @Inject constructor() : CityListPrefMapper {

    companion object {
        private const val DELIMITER = ","
    }

    override fun mapToPref(cities: List<City>): String = cities.joinToString(separator = DELIMITER)

    override fun mapFromPref(cities: String): List<City> = cities
        .split(DELIMITER)
        .map { name -> City(name) }
}