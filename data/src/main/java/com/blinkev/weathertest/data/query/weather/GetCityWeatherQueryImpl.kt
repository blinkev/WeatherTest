package com.blinkev.weathertest.data.query.weather

import com.blinkev.weathertest.data.api.WeatherApi
import com.blinkev.weathertest.data.query.weather.mapper.GetCityWeatherRespMapper
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery
import io.reactivex.Observable
import javax.inject.Inject

class GetCityWeatherQueryImpl @Inject constructor(
    private val api: WeatherApi,
    private val mapper: GetCityWeatherRespMapper
) : GetCityWeatherQuery {

    override fun get(city: City): Observable<List<CityWeather>> = api.getCityWeather(city.name).map(mapper::map)
}