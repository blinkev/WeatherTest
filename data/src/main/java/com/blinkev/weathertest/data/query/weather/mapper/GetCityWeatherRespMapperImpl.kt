package com.blinkev.weathertest.data.query.weather.mapper

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.domain.entity.CityWeather
import javax.inject.Inject

class GetCityWeatherRespMapperImpl @Inject constructor(
    private val dateMapper: DateMapper,
    private val hourMapper: HourWeatherDtoToEntityMapper
): GetCityWeatherRespMapper {

    override fun map(resp: GetCityWeatherResp): List<CityWeather> = resp.data.weather.map { dayDto ->

        val date = dateMapper.map(dayDto.date)

        CityWeather(
            date = date,
            hours = dayDto.hourly.map { hourDto ->
                hourMapper.map(day = date, dto = hourDto)
            }
        )
    }
}