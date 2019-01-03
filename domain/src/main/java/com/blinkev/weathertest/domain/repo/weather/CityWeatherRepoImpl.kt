package com.blinkev.weathertest.domain.repo.weather

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.entity.City
import com.blinkev.weathertest.domain.entity.CityWeather
import com.blinkev.weathertest.domain.util.toDataStatus
import com.blinkev.weathertest.domain.util.toDataStatusWithLoading
import io.reactivex.Observable
import javax.inject.Inject

class CityWeatherRepoImpl @Inject constructor(
    private val query: GetCityWeatherQuery
) : CityWeatherRepo {

    override fun getWeather(city: City): Observable<DataStatus<List<CityWeather>>> = query
        .get(city)
        .toDataStatusWithLoading()
}