package com.blinkev.weathertest.data.query.city.mapper

import javax.inject.Inject

class LocationMapperImpl @Inject constructor() : LocationMapper {

    override fun map(lat: Double, lon: Double): String = "$lat,$lon"
}