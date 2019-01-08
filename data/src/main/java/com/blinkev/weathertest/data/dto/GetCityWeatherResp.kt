package com.blinkev.weathertest.data.dto

data class GetCityWeatherResp(val data: DataDto) {

    data class DataDto(val weather: List<DayWeatherDto>) {
        companion object
    }

    data class DayWeatherDto(
        val date: String,
        val hourly: List<HourWeatherDto>
    ) {
        companion object
    }

    data class HourWeatherDto(
        val time: String,
        val tempC: String,
        val weatherIconUrl: List<StringValueDto>,
        val weatherDesc: List<StringValueDto>
    ) {
        companion object
    }

    data class StringValueDto(val value: String) {
        companion object
    }

    companion object
}