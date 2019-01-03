package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.domain.entity.CityWeather
import java.util.*

interface HourWeatherDtoToEntityMapper {
    fun map(day: Date, dto: GetCityWeatherResp.HourWeatherDto): CityWeather.HourDetails
}