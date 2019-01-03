package com.blinkev.weathertest.domain.repo.weather

import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import io.reactivex.Observable

interface GetCityWeatherQuery {
    fun get(city: City): Observable<List<CityWeather>>
}