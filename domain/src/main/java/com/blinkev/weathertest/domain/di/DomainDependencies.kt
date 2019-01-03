package com.blinkev.weathertest.domain.di

import com.blinkev.weathertest.domain.repo.city.AddCityQuery
import com.blinkev.weathertest.domain.repo.city.GetCityListQuery
import com.blinkev.weathertest.domain.repo.city.RemoveCityQuery
import com.blinkev.weathertest.domain.repo.weather.GetCityWeatherQuery

interface DomainDependencies {
    val getCityListQuery: GetCityListQuery
    val addCityQuery: AddCityQuery
    val removeCityQuery: RemoveCityQuery

    val getCityWeatherQuery: GetCityWeatherQuery
}