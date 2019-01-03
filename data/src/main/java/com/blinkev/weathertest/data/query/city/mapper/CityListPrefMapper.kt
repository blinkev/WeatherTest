package com.blinkev.weathertest.data.query.city.mapper

import com.blinkev.weathertest.domain.entity.City

interface CityListPrefMapper {
    fun mapToPref(cities: List<City>): String
    fun mapFromPref(cities: String): List<City>
}