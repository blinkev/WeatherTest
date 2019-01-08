package com.blinkev.weathertest.data

import com.blinkev.weathertest.data.dto.GetCityWeatherResp
import com.blinkev.weathertest.data.dto.ResolveCityNameResp
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.util.empty
import java.util.*

fun ResolveCityNameResp.StringValueDto.Companion.mock(
    value: String = String.empty()
) = ResolveCityNameResp.StringValueDto(
    value = value
)

fun ResolveCityNameResp.Result.Companion.mock(
    region: List<ResolveCityNameResp.StringValueDto> = emptyList()
) = ResolveCityNameResp.Result(
    region = region
)

fun ResolveCityNameResp.SearchApi.Companion.mock(
    result: List<ResolveCityNameResp.Result> = emptyList()
) = ResolveCityNameResp.SearchApi(
    result = result
)

fun ResolveCityNameResp.Companion.mock(
    searchApi: ResolveCityNameResp.SearchApi = ResolveCityNameResp.SearchApi.mock()
) = ResolveCityNameResp(
    searchApi = searchApi
)

fun GetCityWeatherResp.StringValueDto.Companion.mock(
    value: String = String.empty()
) = GetCityWeatherResp.StringValueDto(
    value = value
)

fun GetCityWeatherResp.HourWeatherDto.Companion.mock(
    time: String = String.empty(),
    tempC: String = "-1",
    weatherIconUrl: List<GetCityWeatherResp.StringValueDto> = emptyList(),
    weatherDesc: List<GetCityWeatherResp.StringValueDto> = emptyList()
) = GetCityWeatherResp.HourWeatherDto(
    time = time,
    tempC = tempC,
    weatherIconUrl = weatherIconUrl,
    weatherDesc = weatherDesc
)

fun GetCityWeatherResp.DayWeatherDto.Companion.mock(
    date: String = String.empty(),
    hourly: List<GetCityWeatherResp.HourWeatherDto> = emptyList()
) = GetCityWeatherResp.DayWeatherDto(
    date = date,
    hourly = hourly
)

fun GetCityWeatherResp.DataDto.Companion.mock(
    weather: List<GetCityWeatherResp.DayWeatherDto> = emptyList()
) = GetCityWeatherResp.DataDto(
    weather = weather
)

fun GetCityWeatherResp.Companion.mock(
    data: GetCityWeatherResp.DataDto = GetCityWeatherResp.DataDto.mock()
) = GetCityWeatherResp(
    data = data
)

fun CityWeather.HourDetails.Companion.mock(
    hour: Date = Date(1),
    temperature: Int = 0,
    description: String = String.empty(),
    iconUrl: String = String.empty()
) = CityWeather.HourDetails(
    hour = hour,
    temperature = temperature,
    description = description,
    iconUrl = iconUrl
)

fun CityWeather.Companion.mock(
    date: Date = Date(1),
    hours: List<CityWeather.HourDetails> = emptyList()
) = CityWeather(
    date = date,
    hours = hours
)