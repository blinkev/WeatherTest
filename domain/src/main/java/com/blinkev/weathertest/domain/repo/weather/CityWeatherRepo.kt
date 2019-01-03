package com.blinkev.weathertest.domain.repo.weather

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import io.reactivex.Observable

interface CityWeatherRepo {
    fun getWeather(city: City): Observable<DataStatus<List<CityWeather>>>
}