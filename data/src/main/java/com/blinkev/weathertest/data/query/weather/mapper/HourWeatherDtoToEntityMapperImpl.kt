package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.util.empty
import java.util.*
import javax.inject.Inject

class HourWeatherDtoToEntityMapperImpl @Inject constructor(
    private val timeMapper: TimeMapper
) : HourWeatherDtoToEntityMapper {

    override fun map(day: Date, dto: GetCityWeatherResp.HourWeatherDto) = CityWeather.HourDetails(
        hour = timeMapper.map(day, dto.time),
        temperature = dto.tempC.toInt(),
        description = dto.weatherDesc.firstOrNull()?.value ?: String.empty(),
        iconUrl = dto.weatherIconUrl.firstOrNull()?.value ?: String.empty()
    )
}