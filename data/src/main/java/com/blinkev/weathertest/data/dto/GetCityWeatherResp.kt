package com.blinkev.weathertest.data.dto

data class GetCityWeatherResp(val data: DataDto) {

    data class DataDto(val weather: List<DayWeatherDto>)

    data class DayWeatherDto(
        val date: String,
        val hourly: List<HourWeatherDto>
    )

    data class HourWeatherDto(
        val time: String,
        val tempC: String,
        val weatherIconUrl: List<StringValueDto>,
        val weatherDesc: List<StringValueDto>
    )

    data class StringValueDto(val value: String)
}