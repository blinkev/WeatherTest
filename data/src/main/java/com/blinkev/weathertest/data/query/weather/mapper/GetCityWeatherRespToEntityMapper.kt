package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.domain.entity.CityWeather

interface GetCityWeatherRespToEntityMapper {
    fun map(resp: GetCityWeatherResp): List<CityWeather>
}