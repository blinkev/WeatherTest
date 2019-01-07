package com.blinkev.weathertest.data.query.city.mapper

interface LocationMapper {
    fun map(lat: Double, lon: Double): String
}