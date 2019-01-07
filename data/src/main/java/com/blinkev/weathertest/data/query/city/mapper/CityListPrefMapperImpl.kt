package com.blinkev.weathertest.data.query.city.mapper

import android.util.Log
import com.blinkev.weathertest.domain.entity.City
import javax.inject.Inject

class CityListPrefMapperImpl @Inject constructor() : CityListPrefMapper {

    companion object {
        private const val DELIMITER = ","
    }

    override fun mapToPref(cities: List<City>): String = cities.map(City::name).joinToString(separator = DELIMITER)

    override fun mapFromPref(cities: String): List<City> =
        if (cities.isBlank()) {
            emptyList()
        } else {
            cities
                .split(DELIMITER)
                .map { name -> City(name) }
        }
}