package com.blinkev.weathertest.domain.di

import com.blinkev.weathertest.domain.repo.city.CityRepo
import com.blinkev.weathertest.domain.repo.weather.CityWeatherRepo

interface DomainProvider {
    val cityRepo: CityRepo
    val cityWeatherRepo: CityWeatherRepo
}